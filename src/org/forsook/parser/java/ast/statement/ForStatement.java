package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.14")
@SuppressWarnings("serial")
public abstract class ForStatement extends Statement implements InnerBlockStatement {

    private Statement body;
    
    public ForStatement() {
    }
    
    public ForStatement(Statement body) {
        this.body = body;
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
        ForStatement other = (ForStatement) obj;
        if (body == null) {
            if (other.body != null) {
                return false;
            }
        } else if (!body.equals(other.body)) {
            return false;
        }
        return true;
    }
}
