package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.VoidType;

@JlsReference("8.4.5")
@ParseletDefinition(
        name = "forsook.java.voidType",
        emits = VoidType.class
)
public class VoidTypeParselet extends TypeParselet<VoidType> {

    @Override
    public VoidType parse(Parser parser) {
        if (!parser.peekPresentAndSkip("void")) {
            return null;
        }
        return new VoidType();
    }

}
