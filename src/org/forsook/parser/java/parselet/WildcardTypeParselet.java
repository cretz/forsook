package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.WildcardType;

@JlsReference("4.5.1")
@ParseletDefinition(
        name = "forsook.java.wildcardType",
        emits = WildcardType.class,
        needs = ReferenceType.class
)
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
            ReferenceType type = parser.next(ReferenceType.class);
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
