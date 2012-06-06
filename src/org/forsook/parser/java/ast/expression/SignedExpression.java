package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.15")
@SuppressWarnings("serial")
public class SignedExpression extends Expression implements UnaryExpression {

    private boolean positive;
    private Expression expression;
    
    public SignedExpression() {
    }

    public SignedExpression(boolean positive, Expression expression) {
        this.positive = positive;
        this.expression = expression;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
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
        result = prime * result + (positive ? 1231 : 1237);
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
        SignedExpression other = (SignedExpression) obj;
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        if (positive != other.positive) {
            return false;
        }
        return true;
    }
}
