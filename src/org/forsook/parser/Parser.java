package org.forsook.parser;

import java.util.List;


public interface Parser {
	
	/**
	 * Return an ordered list of the found parselets, all optional
	 * and all can appear in any order
	 * 
	 * @param parselets
	 * @return
	 */
	List<?> any(Class<? extends Parselet<?>>... parselets);
	
	Character peek();
	
	Character next();
	
	void skip(int charCount);
	
	boolean peekStringPresent(String string);
	
	<U, T extends Parselet<U>> U next(Class<T> parselet);
}
