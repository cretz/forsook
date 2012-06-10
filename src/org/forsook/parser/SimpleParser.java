package org.forsook.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;

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
            if (parselet.getRecursiveMinimumSize() != null) {
                //in the map?
                Map<Integer, RecursiveTypeMemo> map = memoTable.get(type);
                if (map == null) {
                    map = new HashMap<Integer, RecursiveTypeMemo>();
                    memoTable.put(type, map);
                }
                RecursiveTypeMemo memo = map.get(cursor);
                if (memo == null) {
                    memo = new RecursiveTypeMemo(0);
                    map.put(cursor, memo);
                }
                //is this over the length allowed?
                if (memo.minimumNeeded > source.length() - cursor - 1) {
                    return null;
                } else {
                    memo.minimumNeeded += parselet.getRecursiveMinimumSize();
                }
            }
            //hold on to the cursor so we can roll back
            int oldCursor = cursor;
            //parse
            T object = (T) parselet.getParselet().parse(this);
            //actually get something?
            if (object != null) {
                //if we had a recursive minimum, remove it
                if (parselet.getRecursiveMinimumSize() != null) {
                    memoTable.get(type).remove(oldCursor);
                }
                return object;
            }
            //we didn't? reset the cursor
            cursor = oldCursor;
        }
        return null;
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
        return lastKnownLine;
    }
    
    @Override
    public int getColumn() {
        if (cursor != lastLocationCheckCursor) {
            updateLineAndColumn();
        }
        return lastKnownColumn;
    }
    
    private static class RecursiveTypeMemo {
        private int minimumNeeded;
        
        private RecursiveTypeMemo(int minimumNeeded) {
            this.minimumNeeded = minimumNeeded;
        }
    }
}
