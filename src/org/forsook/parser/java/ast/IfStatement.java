package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.9")
@SuppressWarnings("serial")
public class IfStatement extends Statement {

    private Expression condition;
    private Statement thenStatement;
    private Statement elseStatement;
    
    public IfStatement() {
    }
    
    public IfStatement(Expression condition,
            Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    
    public Expression getCondition() {
        return condition;
    }
    
    public void setCondition(Expression condition) {
        this.condition = condition;
    }
    
    public Statement getThenStatement() {
        return thenStatement;
    }
    
    public void setThenStatement(Statement thenStatement) {
        this.thenStatement = thenStatement;
    }
    
    public Statement getElseStatement() {
        return elseStatement;
    }
    
    public void setElseStatement(Statement elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result
                + ((elseStatement == null) ? 0 : elseStatement.hashCode());
        result = prime * result
                + ((thenStatement == null) ? 0 : thenStatement.hashCode());
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
        IfStatement other = (IfStatement) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (elseStatement == null) {
            if (other.elseStatement != null) {
                return false;
            }
        } else if (!elseStatement.equals(other.elseStatement)) {
            return false;
        }
        if (thenStatement == null) {
            if (other.thenStatement != null) {
                return false;
            }
        } else if (!thenStatement.equals(other.thenStatement)) {
            return false;
        }
        return true;
    }
}
