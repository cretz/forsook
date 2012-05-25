package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.WildcardType;

@ParseletDepends(ReferenceTypeParselet.class)
public class WildcardTypeParselet extends TypeParselet<WildcardType> {

    @Override
    public WildcardType parse(Parser parser) {
        if (!parser.peekPresentAndSkip('?')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //extends/super
        boolean extendsType = parser.peekPresentAndSkip("extends");
        if (extendsType || parser.peekPresentAndSkip("super")) {
            parseWhiteSpaceAndComments(parser);
            ReferenceType type = parser.next(ReferenceTypeParselet.class);
            if (type == null) {
                return null;
            } else {
                return new WildcardType(extendsType ? type : null,
                        extendsType ? null : type);
            }
        } else {
            return new WildcardType(null, null);
        }
        
    }

}
