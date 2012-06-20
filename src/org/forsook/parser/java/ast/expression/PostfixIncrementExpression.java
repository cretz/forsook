package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.StatementExpression;

@JlsReference({ "15.14.2", "15.14.3" })
@SuppressWarnings("serial")
public class PostfixIncrementExpression extends Expression 
        implements PostfixExpression, StatementExpression {

    private Expression expression;
    private boolean increment;
    
    public PostfixIncrementExpression() {
    }

    public PostfixIncrementExpression(Expression expression, boolean increment) {
        this.expression = expression;
        this.increment = increment;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
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
        PostfixIncrementExpression other = (PostfixIncrementExpression) obj;
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
