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
    
    /**
     * Return the first found parselet, or null if not found
     * 
     * @param parselets
     * @return
     */
    Object first(Class<? extends Parselet<?>>... parselets);
    
    /**
     * Peek for the next character without moving the cursor
     * 
     * @return The next character or null if end of input
     */
    Character peek();
    
    /**
     * Returns true if next is end of input
     * 
     * @return
     */
    boolean isPeekEndOfInput();
    
    /**
     * Peek for the next character and move the cursor one
     * 
     * @return The next character or null if end of input
     */
    Character next();
    
    /**
     * Returns true if current cursor is end of input
     * 
     * @return
     */
    boolean isEndOfInput();
    
    /**
     * Skip the given number of characters
     * 
     * @param charCount
     */
    void skip(int charCount);
    
    /**
     * Check char presence without moving cursor
     * 
     * @param chr
     * @return
     */
    boolean peekPresent(char chr);
    
    /**
     * Check string presence without moving cursor
     * 
     * @param string
     * @return
     */
    boolean peekPresent(String string);
    
    /**
     * Check char presence without moving cursor and
     * move cursor if true is returned
     * 
     * @param chr
     * @return
     */
    boolean peekPresentAndSkip(char chr);

    
    /**
     * Check string presence and move cursor if true is returned
     * 
     * @param string
     * @return
     */
    boolean peekPresentAndSkip(String string);
    
    /**
     * Try to get the next value for the given parselet class
     * 
     * @param parselet
     * @return The next value or null if not found
     */
    <U, T extends Parselet<U>> U next(Class<T> parselet);
}
