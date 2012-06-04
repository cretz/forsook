package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;

@JlsReference("14.12")
@SuppressWarnings("serial")
public class WhileStatement extends Statement {

    private Expression condition;
    private Statement body;
    
    public WhileStatement() {
    }
    
    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }
    
    public Expression getCondition() {
        return condition;
    }
    
    public void setCondition(Expression condition) {
        this.condition = condition;
    }
    
    public Statement getBody() {
        return body;
    }
    
    public void setBody(Statement body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result
                + ((condition == null) ? 0 : condition.hashCode());
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
        WhileStatement other = (WhileStatement) obj;
        if (body == null) {
            if (other.body != null) {
                return false;
            }
        } else if (!body.equals(other.body)) {
            return false;
        }
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        return true;
    }
}
