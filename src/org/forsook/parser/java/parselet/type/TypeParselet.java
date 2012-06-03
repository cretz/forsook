package org.forsook.parser.java.parselet.type;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.parselet.JavaParselet;

public class TypeParselet<T extends Type> extends JavaParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.first(PrimitiveType.class, ReferenceType.class);
    }
}
