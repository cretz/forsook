package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.4")
@SuppressWarnings("serial")
public class LocalVariableDeclarationStatement extends Statement implements InnerBlockStatement {

    private LocalVariableDeclarationExpression expression;
    
    public LocalVariableDeclarationStatement() {
    }
    
    public LocalVariableDeclarationStatement(LocalVariableDeclarationExpression expression) {
        this.expression = expression;
    }

    public LocalVariableDeclarationExpression getExpression() {
        return expression;
    }
    
    public void setExpression(LocalVariableDeclarationExpression expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((expression == null) ? 0 : expression.hashCode());
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
        LocalVariableDeclarationStatement other = (LocalVariableDeclarationStatement) obj;
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        return true;
    }
}
