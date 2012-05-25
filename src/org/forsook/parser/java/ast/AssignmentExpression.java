package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class AssignmentExpression extends Expression {

    private Expression leftHandSide;
    private AssignmentOperator operator;
    private Expression rightHandSide;
    
    public AssignmentExpression() {
    }
    
    public AssignmentExpression(Expression leftHandSide, AssignmentOperator operator, Expression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
    }
    
    public Expression getLeftHandSide() {
        return leftHandSide;
    }
    
    public void setLeftHandSide(Expression leftHandSide) {
        this.leftHandSide = leftHandSide;
    }
    
    public AssignmentOperator getOperator() {
        return operator;
    }
    
    public void setOperator(AssignmentOperator operator) {
        this.operator = operator;
    }
    
    public Expression getRightHandSide() {
        return rightHandSide;
    }
    
    public void setRightHandSide(Expression rightHandSide) {
        this.rightHandSide = rightHandSide;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((leftHandSide == null) ? 0 : leftHandSide.hashCode());
        result = prime * result
                + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result
                + ((rightHandSide == null) ? 0 : rightHandSide.hashCode());
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
        AssignmentExpression other = (AssignmentExpression) obj;
        if (leftHandSide == null) {
            if (other.leftHandSide != null) {
                return false;
            }
        } else if (!leftHandSide.equals(other.leftHandSide)) {
            return false;
        }
        if (operator != other.operator) {
            return false;
        }
        if (rightHandSide == null) {
            if (other.rightHandSide != null) {
                return false;
            }
        } else if (!rightHandSide.equals(other.rightHandSide)) {
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
