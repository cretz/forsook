package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.23")
@SuppressWarnings("serial")
public class ConditionalAndOperatorExpression extends Expression 
        implements ConditionalAndExpression {

    private Expression left;
    private Expression right;
    
    public ConditionalAndOperatorExpression() {
    }
    
    public ConditionalAndOperatorExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    public Expression getLeft() {
        return left;
    }
    
    public void setLeft(Expression left) {
        this.left = left;
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
        ConditionalAndOperatorExpression other = (ConditionalAndOperatorExpression) obj;
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
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
}
