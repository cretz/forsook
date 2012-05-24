package org.forsook.parser.java.ast;


@SuppressWarnings("serial")
public class VariableDeclarator extends JavaModel {
    
    private VariableDeclaratorId id;
    private Expression init;
    
    public VariableDeclarator() {
    }
    
    public VariableDeclarator(VariableDeclaratorId id, Expression init) {
        this.id = id;
        this.init = init;
    }
    
    public VariableDeclaratorId getId() {
        return id;
    }
    
    public void setId(VariableDeclaratorId id) {
        this.id = id;
    }
    
    public Expression getInit() {
        return init;
    }
    
    public void setInit(Expression init) {
        this.init = init;
    }
}
