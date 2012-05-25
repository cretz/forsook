package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;

@ParseletDepends(IdentifierParselet.class)
public class QualifiedNameParselet extends JavaParselet<String> {

    @Override
    public String parse(Parser parser) {
        //simply put, either an identifier char or a dot
        StringBuilder builder = new StringBuilder();
        do {
            if (builder.length() > 0) {
                builder.append('.');
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //identifier
            String identifier = parser.next(IdentifierParselet.class);
            if (identifier == null) {
                break;
            } else {
                builder.append(identifier);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip('.'));
        if (builder.length() == 0) {
            return null;
        } else {
            return builder.toString();
        }
    }

}
