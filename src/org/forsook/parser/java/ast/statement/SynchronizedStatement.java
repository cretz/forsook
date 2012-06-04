package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.19")
@SuppressWarnings("serial")
public class SynchronizedStatement extends Statement implements StatementWithoutTrailingSubstatement {

    private Expression expression;
    private BlockStatement block;
    
    public SynchronizedStatement() {
    }    
    
    public SynchronizedStatement(Expression expression, BlockStatement block) {
        this.expression = expression;
        this.block = block;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    public BlockStatement getBlock() {
        return block;
    }
    
    public void setBlock(BlockStatement block) {
        this.block = block;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((block == null) ? 0 : block.hashCode());
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
        SynchronizedStatement other = (SynchronizedStatement) obj;
        if (block == null) {
            if (other.block != null) {
                return false;
            }
        } else if (!block.equals(other.block)) {
            return false;
        }
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
