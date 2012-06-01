package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.15")
@SuppressWarnings("serial")
public class UnaryExpression extends Expression {
    
    private Expression expression;
    private UnaryOperator operator;
    
    public UnaryExpression() {
    }
    
    public UnaryExpression(Expression expression, UnaryOperator operator) {
        this.expression = expression;
        this.operator = operator;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    public UnaryOperator getOperator() {
        return operator;
    }
    
    public void setOperator(UnaryOperator operator) {
        this.operator = operator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((expression == null) ? 0 : expression.hashCode());
        result = prime * result
                + ((operator == null) ? 0 : operator.hashCode());
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
        UnaryExpression other = (UnaryExpression) obj;
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        if (operator != other.operator) {
            return false;
        }
        return true;
    }
    
    public static enum UnaryOperator {
        POSITIVE("+"),
        NEGATIVE("-"),
        PRE_INCREMENT("++"),
        PRE_DECREMENT("--"),
        NOT("!"),
        INVERSE("~"),
        POST_INCREMENT("++"),
        POST_DECREMENT("--");
        
        private final String string;
        private final boolean postfix;
        
        private UnaryOperator(String string) {
            this(string, false);
        }
        
        private UnaryOperator(String string, boolean postfix) {
            this.string = string;
            this.postfix = postfix;
        }
        
        public boolean isPostfix() {
            return postfix;
        }
        
        @Override
        public String toString() {
            return string;
        }
    }
}
