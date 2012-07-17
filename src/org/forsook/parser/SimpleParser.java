package org.forsook.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Stack;

import org.forsook.parser.java.ast.lexical.Comment;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.parselet.lexical.CharacterAndStringLiteralExpressionParselet;
import org.forsook.parser.java.parselet.lexical.CommentParselet;

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
    
//    private final NavigableMap<Integer, Integer> lexedNonCode = new TreeMap<Integer, Integer>();
    private final boolean[] nonCode;
    //just commas and braces for now
    private final int[] astDepth;
    
    private final List<String> cantRecurse = new ArrayList<String>();
    
    private int cursor = -1;
    private int lastLocationCheckCursor = 0;
    private int lastKnownLine = 0;
    private int lastKnownColumn = 0;
    private int depth = 0;
    
    public SimpleParser(String source, Map<Class<?>, NavigableSet<ParseletInstance>> parseletMap) {
        this.source = source;
        this.parseletMap = parseletMap;
        int index = 0;
        //TODO: this w/ separate parser please so it'll be up extensions
        CharacterAndStringLiteralExpressionParselet literal = 
                new CharacterAndStringLiteralExpressionParselet();
        CommentParselet comment = new CommentParselet();
        nonCode = new boolean[source.length()];
        while (index < source.length()) {
            switch (source.charAt(index)) {
            case '"':
            case '\'':
                cursor = index - 1;
                LiteralExpression expr = literal.parse(this);
                if (expr == null) {
                    throw new RuntimeException(
                            "Incomplete string/char literal starting at " +
                            getLine() + ":" + getColumn());
                }
                Arrays.fill(nonCode, index, cursor + 1, true);
//                lexedNonCode.put(index, cursor);
                index = cursor + 1;
                continue;
            case '/':
                if (index < source.length() - 1) {
                    switch (source.charAt(index + 1)) {
                    case '/':
                    case '*':
                        cursor = index - 1;
                        Comment cmt = comment.parse(this);
                        if (cmt == null) {
                            throw new RuntimeException();
                        }
//                        lexedNonCode.put(index, cursor);
                        Arrays.fill(nonCode, index, cursor + 1, true);
                        index = cursor + 1;
                        continue;
                    }
                }
            }
            index++;
        }
        //now ast depth
        astDepth = new int[source.length()];
        int currDepth = 0;
        for (int i = 0; i < source.length(); i++) {
            if (!nonCode[i]) {
                switch (source.charAt(i)) {
                case '(':
                case '{':
                    astDepth[i] = ++currDepth;
                    continue;
                case ')':
                case '}':
                    astDepth[i] = currDepth;
                    currDepth--;
                    continue;
                }
            }
            astDepth[i] = currDepth;
        }
        memoTable.clear();
        lookAheads.clear();
//        System.out.println("stack - " + lexedNonCode);
//        for (Entry<Integer, Integer> entry : lexedNonCode.entrySet()) {
//            System.out.println(source.substring(entry.getKey(), 
//                    entry.getValue() + 1));
//        }
        cursor = -1;
        lastLocationCheckCursor = 0;
        lastKnownLine = 0;
        lastKnownColumn = 0;
        depth = 0;
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
    public <T> T next(Class<T> type) {
        return next(type, null);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T next(Class<T> type, Class<?> cantRecurseType) {
        if (cantRecurse.contains(type.getName() + "-" + cursor)) {
            return null;
        }
        String cantRecurseKey = null;
        if (cantRecurseType != null) {
            cantRecurseKey = cantRecurseType.getName() + "-" + cursor;
            cantRecurse.add(cantRecurseKey);
        }
        try {
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
                    } else {
                        memo = map.get(cursor);
                    }
                    if (memo == null) {
                        memo = new RecursiveTypeMemo(0);
                        memo.maxCursor = lookAheads.isEmpty() ?
                                source.length() : lookAheads.peek();
                        memo.maxCursor -= cursor - 1;
                        map.put(cursor, memo);
                    }
                    //is this over the length allowed?
                    if (memo.minimumNeeded > memo.maxCursor) {
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
                depth++;
                //parse
                T object = (T) parselet.getParselet().parse(this);
                depth--;
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
//                if (memo != null && memo.previouslyFound != null) {
//                    //we had one earlier in this cursor and type, so update cursor and return it
//                    cursor = memo.previousFoundEndingCursor;
//                    return (T) memo.previouslyFound;
//                }
                //reset the cursor
                cursor = oldCursor;
            }
            return null;
        } finally {
            if (cantRecurseKey != null) {
                cantRecurse.remove(cantRecurseKey);
            }
        }
    }
    
    @Override
    public boolean pushLookAhead(char... items) {
        Integer previous = lookAheads.isEmpty() ? source.length(): lookAheads.peek();
        int latestIndex = -1;
        for (char item : items) {
//            Entry<Integer, Integer> entry;
            int check = previous - 1;
            do {
                int index = source.lastIndexOf(item, check);
                if (index > cursor && index > latestIndex && index < previous) {
//                    entry = lexedNonCode.floorEntry(index);
//                    if (entry == null || entry.getValue() < index) {
//                        latestIndex = index;
//                        break;
//                    } else {
//                        check = entry.getKey() - 1;
//                    }
                    if (nonCode[index]) {
                        int i;
                        for (i = index; i >= 0; i--) {
                            if (!nonCode[i]) {
                                check = i;
                                break;
                            }
                        }
                        if (i == -1) {
                            throw new RuntimeException();
                        }
                    } else {
                        latestIndex = index;
                        break;
                    }
                } else {
                    break;
                }
            } while (true);
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
//            Entry<Integer, Integer> entry;
            int check = previous - 1;
            do {
                int index = source.lastIndexOf(item, check);
                if (index > cursor && index > latestIndex && index < previous) {
//                    entry = lexedNonCode.floorEntry(index);
//                    if (entry == null || entry.getValue() < index) {
//                        latestIndex = index;
//                        break;
//                    } else {
//                        check = entry.getKey() - 1;
//                    }
                    if (nonCode[index]) {
                        int i;
                        for (i = index; i >= 0; i--) {
                            if (!nonCode[i]) {
                                check = i;
                                break;
                            }
                        }
                        if (i == -1) {
                            throw new RuntimeException();
                        }
                    } else {
                        latestIndex = index;
                        break;
                    }
                } else {
                    break;
                }
            } while (true);
        }
        if (latestIndex != -1) {
            lookAheads.push(latestIndex);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean pushFirstDepthLookAhead(int astDepth, char... items) {
        return pushDepthLookAhead(astDepth, true, items);
    }
    
    @Override
    public boolean pushLastDepthLookAhead(int astDepth, char... items) {
        return pushDepthLookAhead(astDepth, false, items);
    }
    
    private boolean pushDepthLookAhead(int depth, boolean first, char... items) {
        int origFirstOrLast = first ? source.length() : -1;
        int firstOrLastIndex = origFirstOrLast;
        int lastIndex = lookAheads.isEmpty() ? source.length() : lookAheads.peek();
        for (char item : items) {
            int index;
            int from = cursor + 1;
            do {
                index = source.indexOf(item, from);
                if (index >= lastIndex || index == -1 || astDepth[index] < depth) {
                    break;
                } else if (index != -1 && ((first && index < firstOrLastIndex) ||
                        (!first && index > firstOrLastIndex)) &&
                        !nonCode[index] && astDepth[index] == depth) {
                    firstOrLastIndex = index;
                }
                from = index + 1;
            } while (true);
        }
        if (firstOrLastIndex != origFirstOrLast) {
            lookAheads.push(firstOrLastIndex);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean pushFirstDepthLookAhead(int depth, String... items) {
        int firstIndex = source.length();
        int lastIndex = lookAheads.isEmpty() ? source.length() : lookAheads.peek();
        for (String item : items) {
            int index;
            int from = cursor + 1;
            do {
                index = source.indexOf(item, from);
                if (index >= lastIndex) {
                    break;
                } else if (index != -1 && index < firstIndex &&
                        !nonCode[index] && astDepth[index] == depth) {
                    firstIndex = index;
                    break;
                } else if (index == -1) {
                    break;
                } else {
                    from = index + 1;
                }
            } while (true);
        }
        if (firstIndex != source.length()) {
            lookAheads.push(firstIndex);
            return true;
        }
        return false;
    }
    
    @Override
    public void popLookAhead() {
        lookAheads.pop();
    }
    
    @Override
    public int peekAstDepth() {
        return cursor + 1 >= astDepth.length ? Integer.MAX_VALUE : 
            astDepth[cursor + 1];
    }
    
    @Override
    public int getAstDepth() {
        return cursor == -1 ? 0 : astDepth[cursor];
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
    
    @Override
    public int getParseletDepth() {
        return depth;
    }
    
    private static class RecursiveTypeMemo {
        private int minimumNeeded;
        private Object previouslyFound;
        private int previousFoundEndingCursor;
        private int maxCursor;
        
        private RecursiveTypeMemo(int minimumNeeded) {
            this.minimumNeeded = minimumNeeded;
        }
    }
}
