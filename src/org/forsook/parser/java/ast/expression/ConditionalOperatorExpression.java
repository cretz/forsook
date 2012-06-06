package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.25")
@SuppressWarnings("serial")
public class ConditionalOperatorExpression extends Expression implements ConditionalExpression {

    private Expression condition;
    private Expression thenExpression;
    private Expression elseExpression;
    
    public ConditionalOperatorExpression() {
    }

    public ConditionalOperatorExpression(Expression condition,
            Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public Expression getThenExpression() {
        return thenExpression;
    }

    public void setThenExpression(Expression thenExpression) {
        this.thenExpression = thenExpression;
    }

    public Expression getElseExpression() {
        return elseExpression;
    }

    public void setElseExpression(Expression elseExpression) {
        this.elseExpression = elseExpression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result
                + ((elseExpression == null) ? 0 : elseExpression.hashCode());
        result = prime * result
                + ((thenExpression == null) ? 0 : thenExpression.hashCode());
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
        ConditionalOperatorExpression other = (ConditionalOperatorExpression) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (elseExpression == null) {
            if (other.elseExpression != null) {
                return false;
            }
        } else if (!elseExpression.equals(other.elseExpression)) {
            return false;
        }
        if (thenExpression == null) {
            if (other.thenExpression != null) {
                return false;
            }
        } else if (!thenExpression.equals(other.thenExpression)) {
            return false;
        }
        return true;
    }
}
