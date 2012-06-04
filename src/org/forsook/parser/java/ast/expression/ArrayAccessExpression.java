package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.13")
@SuppressWarnings("serial")
public class ArrayAccessExpression extends Expression {

    private Expression name;
    private Expression index;
    
    public ArrayAccessExpression() {
    }
    
    public ArrayAccessExpression(Expression name, Expression index) {
        this.name = name;
        this.index = index;
    }
    
    public Expression getName() {
        return name;
    }
    
    public void setName(Expression name) {
        this.name = name;
    }
    
    public Expression getIndex() {
        return index;
    }
    
    public void setIndex(Expression index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((index == null) ? 0 : index.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        ArrayAccessExpression other = (ArrayAccessExpression) obj;
        if (index == null) {
            if (other.index != null) {
                return false;
            }
        } else if (!index.equals(other.index)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
