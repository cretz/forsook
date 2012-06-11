package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.2")
@SuppressWarnings("serial")
public class BlockStatement extends Statement implements StatementWithoutTrailingSubstatement {
    
    private List<Statement> statements;
    
    public BlockStatement() {
    }
    
    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }
    
    public List<Statement> getStatements() {
        return statements;
    }
    
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((statements == null) ? 0 : statements.hashCode());
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
        BlockStatement other = (BlockStatement) obj;
        if (statements == null) {
            if (other.statements != null) {
                return false;
            }
        } else if (!statements.equals(other.statements)) {
            return false;
        }
        return true;
    }
}
