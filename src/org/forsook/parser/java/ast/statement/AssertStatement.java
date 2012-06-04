package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.10")
@SuppressWarnings("serial")
public class AssertStatement extends Statement implements StatementWithoutTrailingSubstatement {

    private Expression condition;
    private Expression message;
    
    public AssertStatement() {
    }
    
    public AssertStatement(Expression condition, Expression message) {
        this.condition = condition;
        this.message = message;
    }
    
    public Expression getCondition() {
        return condition;
    }
    
    public void setCondition(Expression condition) {
        this.condition = condition;
    }
    
    public Expression getMessage() {
        return message;
    }
    
    public void setMessage(Expression message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
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
        AssertStatement other = (AssertStatement) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }
}
