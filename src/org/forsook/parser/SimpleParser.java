package org.forsook.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleParser implements Parser {

	private final String source;
	private final Map<Class<? extends Parselet<?>>, Parselet<?>> parseletMap;
	
	private int cursor = -1;
	
	public SimpleParser(String source, Map<Class<? extends Parselet<?>>, Parselet<?>> parseletMap) {
		this.source = source;
		this.parseletMap = parseletMap;
	}
	
	@Override
	public List<?> any(Class<? extends Parselet<?>>... parselets) {
		List<Object> ret = new ArrayList<Object>();
		boolean found;
		do {
			found = false;
			for (Class<? extends Parselet<?>> parseletClass : parselets) {
				Object object = next(parseletClass);
				if (object != null) {
					found = true;
					ret.add(object);
				}
			}
		} while (found);
		return ret;
	}

    @Override
	public Object first(Class<? extends Parselet<?>>... parselets) {
	    for (Class<? extends Parselet<?>> parselet : parselets) {
	        Object object = next(parselet);
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
	public <U, T extends Parselet<U>> U next(Class<T> parselet) {
		T parseletImpl = (T) parseletMap.get(parselet);
		if (parseletImpl == null) {
			return null;
		}
		int oldCursor = cursor;
		U object = parseletImpl.parse(this);
		if (object != null) {
			return object;
		}
		cursor = oldCursor;
		return null;
	}
}
