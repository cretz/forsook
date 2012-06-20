package org.forsook.parser;

import java.util.List;

/**
 * Parser interface
 *
 * @author Chad Retz
 */
public interface Parser {
    
    /**
     * Return an ordered list of the found types, all optional
     * and all can appear in any order.
     * 
     * @param types 
     * @return The list of items, or an empty list if none found
     */
    List<?> any(Class<?>... types);
    
    /**
     * Return the first found type, or null if not found
     * 
     * @param types
     * @return
     */
    Object first(Class<?>... types);
    
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
     * Try to get the next value for the given type
     * 
     * @param parselet
     * @return The next value or null if not found
     */
    <T> T next(Class<T> type);
    
    /**
     * Checks to see whether any of the given items are 
     * anywhere in the source starting at the cursor of
     * the last look ahead.
     * 
     * @param items
     * @return
     */
    boolean pushLookAhead(char... items);
    
    /**
     * Checks to see whether any of the given items are 
     * anywhere in the source starting at the cursor of
     * the last look ahead.
     * 
     * @param items
     * @return
     */
    boolean pushLookAhead(String... items);
    
    /**
     * Pop the look ahead that was pushed previously
     */
    void popLookAhead();
    
    /**
     * Get the current cursor position
     * 
     * @return
     */
    int getCursor();
    
    /**
     * Get the current cursor line. Note, if {@link #setCursor(int)} is called,
     * the cache is reset for this making it slower.
     * 
     * @return
     */
    int getLine();
    
    /**
     * Get the current cursor column. Note, if {@link #setCursor(int)} is called,
     * the cache is reset for this making it slower.
     * 
     * @return
     */
    int getColumn();
}
