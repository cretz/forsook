package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.ast.lexical.Identifier;

@SuppressWarnings("serial")
public class IdentifierExpression extends Expression {

    private Identifier identifier;
    
    public IdentifierExpression() {
    }
    
    public IdentifierExpression(Identifier identifier) {
        this.identifier = identifier;
    }
    
    public Identifier getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identifier == null) ? 0 : identifier.hashCode());
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
        IdentifierExpression other = (IdentifierExpression) obj;
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
            return false;
        }
        return true;
    }
}
