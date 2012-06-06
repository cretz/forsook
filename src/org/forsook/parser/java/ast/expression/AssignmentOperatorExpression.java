package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.26")
@SuppressWarnings("serial")
public class AssignmentOperatorExpression extends Expression {

    private Expression left;
    private AssignmentOperator operator;
    private Expression right;
    
    public AssignmentOperatorExpression() {
    }
    
    public AssignmentOperatorExpression(Expression left, 
            AssignmentOperator operator, Expression right) {
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
    
    public AssignmentOperator getOperator() {
        return operator;
    }
    
    public void setOperator(AssignmentOperator operator) {
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
        result = prime * result
                + ((left == null) ? 0 : left.hashCode());
        result = prime * result
                + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result
                + ((right == null) ? 0 : right.hashCode());
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
        AssignmentOperatorExpression other = (AssignmentOperatorExpression) obj;
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

    public static enum AssignmentOperator {
        AND("&="),
        ASSIGN("="),
        DIVIDE("/="),
        LEFT_SHIFT("<<="),
        MINUS("-="),
        MODULO("%="),
        MULTIPLY("*="),
        OR("|="),
        PLUS("+="),
        RIGHT_SIGNED_SHIFT(">>="),
        RIGHT_UNSIGNED_SHIFT(">>>="),
        XOR("^=");

        private final String string;
        
        private AssignmentOperator(String string) {
            this.string = string;
        }
        
        @Override
        public String toString() {
            return string;
        }
    }
}
