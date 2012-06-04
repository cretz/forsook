package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.18")
@SuppressWarnings("serial")
public class ThrowStatement extends Statement implements StatementWithoutTrailingSubstatement {

    private Expression expression;
    
    public ThrowStatement() {
    }    
    
    public ThrowStatement(Expression expression) {
        this.expression = expression;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
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
        ThrowStatement other = (ThrowStatement) obj;
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
