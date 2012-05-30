package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.PrimitiveType;
import org.forsook.parser.java.ast.PrimitiveType.Primitive;

@JlsReference("4.2")
@ParseletDefinition(
        name = "forsook.java.primitiveType",
        emits = PrimitiveType.class
)
public class PrimitiveTypeParselet extends JavaParselet<PrimitiveType> {

    @Override
    public PrimitiveType parse(Parser parser) {
        Primitive primitive = null;
        String ident = nextWord(parser);
        if (ident == null) {
            return null;
        } else if ("boolean".equals(ident)) {
            primitive = Primitive.BOOLEAN;
        } else if ("byte".equals(ident)) {
            primitive = Primitive.BYTE;
        } else if ("char".equals(ident)) {
            primitive = Primitive.CHAR;
        } else if ("double".equals(ident)) {
            primitive = Primitive.DOUBLE;
        } else if ("float".equals(ident)) {
            primitive = Primitive.FLOAT;
        } else if ("int".equals(ident)) {
            primitive = Primitive.INT;
        } else if ("long".equals(ident)) {
            primitive = Primitive.LONG;
        } else if ("short".equals(ident)) {
            primitive = Primitive.SHORT;
        } else {
            return null;
        }
        return new PrimitiveType(primitive);
    }

}
