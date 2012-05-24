package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class ExpressionStatement extends Statement {

    private Expression expression;
    
    public ExpressionStatement() {
    }
    
    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
