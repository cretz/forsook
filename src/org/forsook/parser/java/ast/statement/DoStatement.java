package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;

@JlsReference("14.13")
@SuppressWarnings("serial")
public class DoStatement extends Statement implements StatementWithoutTrailingSubstatement {

    private Statement statement;
    private Expression condition;
    
    public DoStatement() {
    }
    
    public DoStatement(Statement statement, Expression condition) {
        this.statement = statement;
        this.condition = condition;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    public Expression getCondition() {
        return condition;
    }
    
    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result
                + ((statement == null) ? 0 : statement.hashCode());
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
        DoStatement other = (DoStatement) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (statement == null) {
            if (other.statement != null) {
                return false;
            }
        } else if (!statement.equals(other.statement)) {
            return false;
        }
        return true;
    }
}
