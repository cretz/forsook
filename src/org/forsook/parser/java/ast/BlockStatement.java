package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class BlockStatement extends Statement {
    
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
}
