package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.15")
@SuppressWarnings("serial")
public class NegatedExpression extends Expression implements UnaryNotPlusMinusExpression {

    private boolean not;
    private Expression expression;
    
    public NegatedExpression() {
    }

    public NegatedExpression(boolean not, Expression expression) {
        this.not = not;
        this.expression = expression;
    }

    public boolean isNot() {
        return not;
    }
    
    public void setNot(boolean not) {
        this.not = not;
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
        result = prime * result + (not ? 1231 : 1237);
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
        NegatedExpression other = (NegatedExpression) obj;
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        if (not != other.not) {
            return false;
        }
        return true;
    }
}
