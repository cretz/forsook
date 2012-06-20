package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("14.7")
@SuppressWarnings("serial")
public class LabeledStatement extends Statement implements InnerBlockStatement {

    private Identifier identifier;
    private Statement statement;
    
    public LabeledStatement() {
    }
    
    public LabeledStatement(Identifier identifier, Statement statement) {
        this.identifier = identifier;
        this.statement = statement;
    }
    
    public Identifier getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identifier == null) ? 0 : identifier.hashCode());
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
        LabeledStatement other = (LabeledStatement) obj;
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
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
