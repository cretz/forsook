package org.forsook.parser.java.ast.name;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PostfixExpression;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("6.5")
@SuppressWarnings("serial")
public class QualifiedName extends Expression implements PostfixExpression {

    private List<Identifier> identifiers;
    
    public QualifiedName() {
    }
    
    public QualifiedName(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }
    
    public List<Identifier> getIdentifiers() {
        return identifiers;
    }
    
    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identifiers == null) ? 0 : identifiers.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QualifiedName other = (QualifiedName) obj;
        if (identifiers == null) {
            if (other.identifiers != null) {
                return false;
            }
        } else if (!identifiers.equals(other.identifiers)) {
            return false;
        }
        return true;
    }
}
