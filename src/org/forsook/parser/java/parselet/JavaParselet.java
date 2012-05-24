package org.forsook.parser.java.parselet;

import java.util.List;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;

public abstract class JavaParselet<T> implements Parselet<T> {


    @SuppressWarnings("unchecked")
    protected List<?> parseWhiteSpaceAndComments(Parser parser) {
        return parser.any(WhiteSpaceParselet.class, CommentParselet.class);
    }
    
    /**
     * Parse the array dimensions of a type (e.g. [][][])
     * 
     * @param parser
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Integer parseArrayBracketCount(Parser parser) {
        int arrayCount = 0;
        do {
            parser.any(WhiteSpaceParselet.class, CommentParselet.class);
            if (!parser.peekPresentAndSkip('[')) {
                return arrayCount;
            }
            parser.any(WhiteSpaceParselet.class, CommentParselet.class);
            if (!parser.peekPresentAndSkip(']')) {
                return null;
            }
            arrayCount++;
        } while (true);
    }
}
