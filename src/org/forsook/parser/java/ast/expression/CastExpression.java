package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ReferenceType;

@JlsReference("15.16")
@SuppressWarnings("serial")
public class CastExpression extends Expression {

    private ReferenceType type;
    private Expression expression;
    
    public CastExpression() {
    }
    
    public CastExpression(ReferenceType type, Expression expression) {
        this.type = type;
        this.expression = expression;
    }
    
    public ReferenceType getType() {
        return type;
    }
    
    public void setType(ReferenceType type) {
        this.type = type;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((expression == null) ? 0 : expression.hashCode());
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
        CastExpression other = (CastExpression) obj;
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
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
