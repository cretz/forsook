package org.forsook.parser.java.parselet;

import java.util.List;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;

@ParseletDepends({
    WhiteSpaceParselet.class,
    CommentParselet.class
})
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
    protected Integer parseArrayBracketCount(Parser parser) {
        int arrayCount = 0;
        do {
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip('[')) {
                return arrayCount;
            }
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip(']')) {
                return null;
            }
            arrayCount++;
        } while (true);
    }
    
    /**
     * The next word in the parser that is a java identifier (doesn't check keywords).
     * 
     * @param parser
     * @return Null if word not found
     */
    protected String nextWord(Parser parser) {
        StringBuilder identifier = new StringBuilder();
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            }
            if ((identifier.length() == 0 && Character.isJavaIdentifierStart(chr)) ||
                    (identifier.length() > 0 && Character.isJavaIdentifierPart(chr))) {
                identifier.append(chr);
                parser.skip(1);
            } else {
                break;
            }
        } while (true);
        //empty means not there
        if (identifier.length() == 0) {
            return null;
        }
        return identifier.toString();
    }
}
