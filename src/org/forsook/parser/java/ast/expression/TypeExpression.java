package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.ast.type.Type;

@SuppressWarnings("serial")
public class TypeExpression extends Expression {

    private Type type;
    
    public TypeExpression() {
    }
    
    public TypeExpression(Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        TypeExpression other = (TypeExpression) obj;
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
    
    
}
