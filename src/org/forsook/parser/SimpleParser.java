package org.forsook.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Stack;

/**
 * Simple parser supporting a string-based source file and a map of parselets
 *
 * @author Chad Retz
 */
public class SimpleParser implements Parser {

    private final String source;
    private final Map<Class<?>, NavigableSet<ParseletInstance>> parseletMap;
    
    private final Map<Class<?>, Map<Integer, RecursiveTypeMemo>> memoTable =
            new HashMap<Class<?>, Map<Integer, RecursiveTypeMemo>>();
    
    private final Stack<Integer> lookAheads = new Stack<Integer>();
    
    private int cursor = -1;
    private int lastLocationCheckCursor = 0;
    private int lastKnownLine = 0;
    private int lastKnownColumn = 0;
    
    public SimpleParser(String source, Map<Class<?>, NavigableSet<ParseletInstance>> parseletMap) {
        this.source = source;
        this.parseletMap = parseletMap;
    }
    
    @Override
    public List<?> any(Class<?>... types) {
        List<Object> ret = new ArrayList<Object>();
        boolean found;
        do {
            found = false;
            for (Class<?> type : types) {
                Object object = next(type);
                if (object != null) {
                    found = true;
                    ret.add(object);
                }
            }
        } while (found);
        return ret;
    }

    @Override
    public Object first(Class<?>... types) {
        for (Class<?> type : types) {
            Object object = next(type);
            if (object != null) {
                return object;
            }
        }
        return null;
    }

    @Override
    public Character peek() {
        return isPeekEndOfInput() ? null : source.charAt(cursor + 1);
    }
    
    @Override
    public boolean isPeekEndOfInput() {
        return cursor + 1 >= source.length();
    }

    @Override
    public Character next() {
        cursor++;
        return isEndOfInput() ? null : source.charAt(cursor);
    }
    
    @Override
    public boolean isEndOfInput() {
        return cursor >= source.length();
    }

    @Override
    public void skip(int charCount) {
        cursor += charCount;
    }
    
    @Override
    public boolean peekPresent(char chr) {
        return !isPeekEndOfInput() && source.charAt(cursor + 1) == chr;
    }

    @Override
    public boolean peekPresent(String string) {
        return source.regionMatches(cursor + 1, string, 0, string.length());
    }
    
    @Override
    public boolean peekPresentAndSkip(char chr) {
        if (peekPresent(chr)) {
            skip(1);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean peekPresentAndSkip(String string) {
        if (peekPresent(string)) {
            skip(string.length());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T next(Class<T> type) {
        NavigableSet<ParseletInstance> parseletSet = parseletMap.get(type);
        if (parseletSet == null) {
            throw new RuntimeException("No parselets that emit type " + type);
        }
        for (ParseletInstance parselet : parseletSet) {
            //need to check whether this is impossible recursive
            RecursiveTypeMemo memo = null;
            if (parselet.getRecursiveMinimumSize() != null) {
                //in the map?
                Map<Integer, RecursiveTypeMemo> map = memoTable.get(type);
                if (map == null) {
                    map = new HashMap<Integer, RecursiveTypeMemo>();
                    memoTable.put(type, map);
                }
                memo = map.get(cursor);
                if (memo == null) {
                    memo = new RecursiveTypeMemo(0);
                    map.put(cursor, memo);
                }
                //is this over the length allowed?
                if (memo.minimumNeeded > source.length() - cursor - 1) {
                    //did we have one here before?
                    if (memo.previouslyFound != null) {
                        //we did, so reset the cursor and return the object
                        cursor = memo.previousFoundEndingCursor;
                        return (T) memo.previouslyFound;
                    }
                    //nope, recursion gone too far
                    return null;
                } else {
                    memo.minimumNeeded += parselet.getRecursiveMinimumSize();
                }
            }
            //hold on to the cursor so we can roll back
            int oldCursor = cursor;
            //holf on to the lookahead stack size, so we can roll back
            int lookAheadStackSize = lookAheads.size();
            //parse
            T object = (T) parselet.getParselet().parse(this);
            //rollback minimum needed
            if (memo != null) {
                memo.minimumNeeded -= parselet.getRecursiveMinimumSize();
            }
            //actually get something?
            if (object != null) {
                //if we had a recursive minimum, say we found an object
                if (memo != null) {
                    memo.previouslyFound = object;
                    memo.previousFoundEndingCursor = cursor;
                }
                return object;
            }
            //reset the lookahead stack
            if (lookAheads.size() > lookAheadStackSize) {
                lookAheads.subList(lookAheadStackSize, lookAheads.size()).clear();
            }
            //check for memo
            if (memo != null && memo.previouslyFound != null) {
                //we had one earlier in this cursor and type, so update cursor and return it
                cursor = memo.previousFoundEndingCursor;
                return (T) memo.previouslyFound;
            }
            //reset the cursor
            cursor = oldCursor;
        }
        return null;
    }
    
    @Override
    public boolean pushLookAhead(char... items) {
        Integer previous = lookAheads.isEmpty() ? source.length(): lookAheads.peek();
        int latestIndex = -1;
        for (char item : items) {
            int index = source.lastIndexOf(item, previous - 1);
            if (index > cursor && index > latestIndex) {
                latestIndex = index;
            }
        }
        if (latestIndex != -1) {
            lookAheads.push(latestIndex);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean pushLookAhead(String... items) {
        Integer previous = lookAheads.isEmpty() ? source.length() : lookAheads.peek();
        int latestIndex = -1;
        for (String item : items) {
            int index = source.lastIndexOf(item, previous - 1);
            if (index > cursor && index > latestIndex) {
                latestIndex = index;
            }
        }
        if (latestIndex != -1) {
            lookAheads.push(latestIndex);
            return true;
        }
        return false;
    }
    
    @Override
    public void popLookAhead() {
        lookAheads.pop();
    }
    
    @Override
    public int getCursor() {
        return cursor;
    }
    
    private void updateLineAndColumn() {
        if (lastLocationCheckCursor > cursor) {
            lastLocationCheckCursor = 0;
            lastKnownLine = 0;
            lastKnownColumn = 0;
        }
        for (int i = lastLocationCheckCursor; i <= cursor && i < source.length(); i++) {
            //is a new line
            char chr = source.charAt(i);
            if (chr == '\n' || (chr == '\r' && 
                    (i + 1 == source.length() || source.charAt(i + 1) != '\n'))) {
                //reset
                lastKnownLine++;
                lastKnownColumn = 0;
            } else {
                lastKnownColumn++;
            }
        }
        lastLocationCheckCursor = cursor;
    }
    
    @Override
    public int getLine() {
        if (cursor != lastLocationCheckCursor) {
            updateLineAndColumn();
        }
        return lastKnownLine + 1;
    }
    
    @Override
    public int getColumn() {
        if (cursor != lastLocationCheckCursor) {
            updateLineAndColumn();
        }
        return lastKnownColumn + 1;
    }
    
    @Override
    public void backupCursor() {
        cursor--;
    }
    
    private static class RecursiveTypeMemo {
        private int minimumNeeded;
        private Object previouslyFound;
        private int previousFoundEndingCursor;
        
        private RecursiveTypeMemo(int minimumNeeded) {
            this.minimumNeeded = minimumNeeded;
        }
    }
}
