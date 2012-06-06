package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.TypeArguments;

@JlsReference("8.8.7.1")
@SuppressWarnings("serial")
public class NonWildTypeArguments extends TypeArguments {

    public NonWildTypeArguments() {
    }
    
    public NonWildTypeArguments(List<Type> types) {
        super(types);
    }
}
