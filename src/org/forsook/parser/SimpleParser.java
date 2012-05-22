package org.forsook.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleParser implements Parser {

	private final String source;
	private final Map<Class<? extends Parselet<?>>, Parselet<?>> parseletMap;
	
	private int cursor = 0;
	
	public SimpleParser(String source, 
			Map<Class<? extends Parselet<?>>, Parselet<?>> parseletMap) {
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
	public Character peek() {
		if (cursor + 1 >= source.length()) {
			return null;
		} else {
			return source.charAt(cursor + 1);
		}
	}

	@Override
	public Character next() {
		if (cursor >= source.length()) {
			return null;
		} else {
			return source.charAt(cursor);
		}
	}

	@Override
	public void skip(int charCount) {
		cursor += charCount;
	}

	@Override
	public boolean peekStringPresent(String string) {
		return source.regionMatches(cursor, string, 0, string.length());
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
