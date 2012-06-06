package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.17")
@SuppressWarnings("serial")
public class MultiplicativeOperatorExpression extends Expression 
        implements MultiplicativeExpression {

    private Expression left;
    private MultiplicativeOperator operator;
    private Expression right;
    
    public MultiplicativeOperatorExpression() {
    }
    
    public MultiplicativeOperatorExpression(Expression left,
            MultiplicativeOperator operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public MultiplicativeOperator getOperator() {
        return operator;
    }

    public void setOperator(MultiplicativeOperator operator) {
        this.operator = operator;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result
                + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
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
        MultiplicativeOperatorExpression other = (MultiplicativeOperatorExpression) obj;
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
            return false;
        }
        if (operator != other.operator) {
            return false;
        }
        if (right == null) {
            if (other.right != null) {
                return false;
            }
        } else if (!right.equals(other.right)) {
            return false;
        }
        return true;
    }

    public static enum MultiplicativeOperator {
        MULTIPLY,
        DIVIDE,
        REMAINDER
    }
}
