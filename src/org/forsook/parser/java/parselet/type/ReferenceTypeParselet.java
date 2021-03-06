package org.forsook.parser.java.parselet.type;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.ReferenceType;

@JlsReference("4.3")
@ParseletDefinition(
        name = "forsook.java.referenceType",
        emits = ReferenceType.class,
        needs = { ClassOrInterfaceType.class, ArrayType.class }
)
public class ReferenceTypeParselet extends TypeParselet<ReferenceType> {
    
    @Override
    public ReferenceType parse(Parser parser) {
        return (ReferenceType) parser.first(ArrayType.class, ClassOrInterfaceType.class);
    }

}
