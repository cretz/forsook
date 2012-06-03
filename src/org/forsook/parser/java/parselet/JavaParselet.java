package org.forsook.parser.java.parselet;

import java.util.List;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.WhiteSpace;

@ParseletDefinition(
        needs = { WhiteSpace.class, Comment.class }
)
public abstract class JavaParselet<T> implements Parselet<T> {

    protected List<?> parseWhiteSpaceAndComments(Parser parser) {
        return parser.any(WhiteSpace.class, Comment.class);
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
