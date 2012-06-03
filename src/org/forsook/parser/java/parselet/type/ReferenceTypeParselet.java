package org.forsook.parser.java.parselet.type;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.TypeVariable;

@JlsReference("4.3")
@ParseletDefinition(
        name = "forsook.java.referenceType",
        emits = ReferenceType.class
)
public class ReferenceTypeParselet extends TypeParselet<ReferenceType> {
    
    @Override
    public ReferenceType parse(Parser parser) {
        return (ReferenceType) parser.first(ClassOrInterfaceType.class,
                TypeVariable.class, ArrayType.class);
    }

}
