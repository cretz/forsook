package org.forsook.parser;

import java.util.ArrayList;
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
    
    private int cursor = -1;
    
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
            int oldCursor = cursor;
            T object = (T) parselet.getParselet().parse(this);
            if (object != null) {
                return object;
            }
            cursor = oldCursor;
        }
        return null;
    }
    
    @Override
    public int getCursor() {
        return cursor;
    }
    
    @Override
    public void setCursor(int cursor) {
        this.cursor = cursor;
    }
}
