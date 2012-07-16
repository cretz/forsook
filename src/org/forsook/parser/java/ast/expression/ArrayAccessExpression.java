package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.13")
@SuppressWarnings("serial")
public class ArrayAccessExpression extends Expression
        implements PrimaryNoNewArrayExpression, ScopedExpression {

    private Expression scope;
    private Expression index;
    
    public ArrayAccessExpression() {
    }
    
    public ArrayAccessExpression(Expression scope, Expression index) {
        this.scope = scope;
        this.index = index;
    }
    
    public Expression getScope() {
        return scope;
    }
    
    public void setScope(Expression scope) {
        this.scope = scope;
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
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
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
        if (scope == null) {
            if (other.scope != null) {
                return false;
            }
        } else if (!scope.equals(other.scope)) {
            return false;
        }
        return true;
    }
}
