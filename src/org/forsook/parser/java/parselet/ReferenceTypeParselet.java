package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.PrimitiveType;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.Type;

@JlsReference("4.3")
@ParseletDefinition(
        name = "forsook.java.referenceType",
        emits = ReferenceType.class,
        needs = { PrimitiveType.class, ClassOrInterfaceType.class }
)
public class ReferenceTypeParselet extends TypeParselet<ReferenceType> {

    //TODO: try to determine whether my different meaning for "ReferenceType" is ok
    
    @Override
    public ReferenceType parse(Parser parser) {
        //try to get the source type
        //primitive?
        Type type = parser.next(PrimitiveType.class);
        if (type == null) {
            //class or interface?
            type = parser.next(ClassOrInterfaceType.class);
            if (type == null) {
                return null;
            }
        }
        //now just get the different array bounds
        Integer arrayCount = parseArrayBracketCount(parser);
        return arrayCount == null ? null : new ReferenceType(type, arrayCount);
    }

}
