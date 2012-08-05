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

import com.eaio.stringsearch.BoyerMooreHorspoolRaita;

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
    
    private final boolean[] nonCode;
    //just commas and braces for now
    private final int[] astDepth;
    
    private final List<String> cantRecurse = new ArrayList<String>();
    
    private int cursor = -1;
    private int lastLocationCheckCursor = 0;
    private int lastKnownLine = 0;
    private int lastKnownColumn = 0;

    private final Stack<ParseletInstance> parseletStack = new Stack<ParseletInstance>();
    
    private final char[] reversedSource;
    private final BoyerMooreHorspoolRaita searcher =
            new BoyerMooreHorspoolRaita();
    
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
        reversedSource = new char[source.length()];
        for (int i = 0; i < source.length(); i++) {
            char chr = source.charAt(i);
            reversedSource[source.length() - i - 1] = chr;
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
        //process
        memoTable.clear();
        lookAheads.clear();
        cursor = -1;
        lastLocationCheckCursor = 0;
        lastKnownLine = 0;
        lastKnownColumn = 0;
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
        String typeKey = type.getName() + "-" + cursor;
        if (cantRecurse.contains(typeKey)) {
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
                parseletStack.push(parselet);
                //times
                long ms = System.currentTimeMillis();
                //parse
                T object = (T) parselet.getParselet().parse(this);
                //times
                parselet.incrementTotalCount(System.currentTimeMillis() - ms);
                parseletStack.pop();
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
            int check = previous - 1;
            do {
                //int index = source.lastIndexOf(item, check);
                int index = lastIndexOf(item, cursor + 1, check);
                if (index > cursor && index > latestIndex && index < previous) {
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
    
    private int lastIndexOf(String item, int start, int end) {
        char[] reversed = new char[item.length()];
        for (int i = 0; i < reversed.length; i++) {
            reversed[reversed.length - i - 1] = item.charAt(i);
        }
        return lastIndexOf(reversed, start, end);
    }
    
    private int lastIndexOf(char item, int start, int end) {
        return lastIndexOf(new char[] { item }, start, end);
    }
    
    private int lastIndexOf(char[] reversed, int start, int end) {
        if (start > end || start < 0) {
            return -1;
        }
        int index = searcher.searchChars(reversedSource, 
                source.length() - end - 1,
                source.length() - start,
                reversed);
        if (index != -1) {
            index = source.length() - index - reversed.length;
        }
        return index;
    }
    
    @Override
    public boolean pushLookAhead(String... items) {
        Integer previous = lookAheads.isEmpty() ? source.length() : lookAheads.peek();
        int latestIndex = -1;
        for (String item : items) {
            int check = previous - 1;
            do {
                //int index = source.lastIndexOf(item, check);
                int index = lastIndexOf(item, cursor + 1, check);
                if (index > cursor && index > latestIndex && index < previous) {
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
                    //if we're the last, let's 
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
    public boolean pushFirstLookAheadNoDeeperThan(int depth, char item) {
        int lastIndex = lookAheads.isEmpty() ? source.length() : lookAheads.peek();
        int index;
        int from = cursor + 1;
        do {
            index = source.indexOf(item, from);
            if (index >= lastIndex || index == -1) {
                return false;
            } else if (!nonCode[index] && astDepth[index] <= depth) {
                lookAheads.push(index);
                return true;
            }
            from = index + 1;
        } while (true);
    }
    
    @Override
    public boolean pushLastLookAheadNoDeeperThan(int depth, char item) {
        int start = cursor + 1;
        int end = lookAheads.isEmpty() ? source.length() - 1 : lookAheads.peek() - 1;
        int index;
        do {
            index = lastIndexOf(item, start, end);
            if (index == -1) {
                return false;
            } else if (!nonCode[index] && astDepth[index] <= depth) {
                lookAheads.push(index);
                return true;
            }
            end = index - 1;
        } while (true);
    }
    
    @Override
    public boolean pushFirstDepthLookAhead(int depth, String... items) {
        return pushDepthLookAhead(depth, true, items);
    }
    
    @Override
    public boolean pushLastDepthLookAhead(int depth, String... items) {
        return pushDepthLookAhead(depth, false, items);
    }
    
    private boolean pushDepthLookAhead(int depth, boolean first, String... items) {
        int origFirstOrLast = first ? source.length() : -1;
        int firstOrLastIndex = origFirstOrLast;
        int lastIndex = lookAheads.isEmpty() ? source.length() : lookAheads.peek();
        for (String item : items) {
            int index;
            int from = cursor + 1;
            do {
                index = source.indexOf(item, from);
                if (index >= lastIndex || index == -1 || astDepth[index] < depth) {
                    break;
                } else if (index != -1 && ((first && index < firstOrLastIndex) ||
                        (!first && index > firstOrLastIndex)) &&
                        !nonCode[index] && astDepth[index] == depth) {
                    //if we're the last, let's 
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
        if (cursor < 0) {
            return 0;
        } else if (cursor != lastLocationCheckCursor) {
            updateLineAndColumn();
        }
        return lastKnownLine + 1;
    }
    
    @Override
    public int getColumn() {
        if (cursor < 0) {
            return 0;
        } else if (cursor != lastLocationCheckCursor) {
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
        return parseletStack.size();
    }
    
    @Override
    public Stack<ParseletInstance> getParseletStack() {
        return parseletStack;
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
