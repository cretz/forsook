package org.forsook.parser.java.ast.name;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("6.5")
@SuppressWarnings("serial")
public class QualifiedName extends Expression {

    private List<Identifier> identifiers;
    
    public QualifiedName(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }
}
