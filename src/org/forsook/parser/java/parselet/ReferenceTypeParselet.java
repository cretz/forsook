package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.Type;

@ParseletDepends({
    PrimitiveTypeParselet.class,
    ClassOrInterfaceTypeParselet.class
})
public class ReferenceTypeParselet extends TypeParselet<ReferenceType> {

    @Override
    public ReferenceType parse(Parser parser) {
        //try to get the source type
        //primitive?
        Type type = parser.next(PrimitiveTypeParselet.class);
        if (type == null) {
            //class or interface?
            type = parser.next(ClassOrInterfaceTypeParselet.class);
            if (type == null) {
                return null;
            }
        }
        //now just get the different array bounds
        Integer arrayCount = parseArrayBracketCount(parser);
        return arrayCount == null ? null : new ReferenceType(type, arrayCount);
    }

}
