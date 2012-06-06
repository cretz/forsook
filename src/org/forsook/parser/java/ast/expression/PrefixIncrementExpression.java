package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.15")
@SuppressWarnings("serial")
public class PrefixIncrementExpression extends Expression implements UnaryExpression {

    private boolean increment;
    private Expression expression;
    
    public PrefixIncrementExpression() {
    }
    
    public PrefixIncrementExpression(boolean increment, Expression expression) {
        this.increment = increment;
        this.expression = expression;
    }
    
    public boolean isIncrement() {
        return increment;
    }
    
    public void setIncrement(boolean increment) {
        this.increment = increment;
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
        result = prime * result + (increment ? 1231 : 1237);
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
        PrefixIncrementExpression other = (PrefixIncrementExpression) obj;
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        if (increment != other.increment) {
            return false;
        }
        return true;
    }
}
