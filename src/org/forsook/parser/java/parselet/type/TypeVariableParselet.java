package org.forsook.parser.java.parselet.type;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.type.TypeVariable;

public class TypeVariableParselet extends TypeParselet<TypeVariable> {

    @Override
    public TypeVariable parse(Parser parser) {
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        return new TypeVariable(name);
    }

}
