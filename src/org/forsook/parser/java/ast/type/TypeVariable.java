package org.forsook.parser.java.ast.type;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Identifier;

@JlsReference("4.3")
@SuppressWarnings("serial")
public class TypeVariable extends ReferenceType {

    private Identifier name;
    
    public TypeVariable(Identifier name) {
        this.name = name;
    }
}
