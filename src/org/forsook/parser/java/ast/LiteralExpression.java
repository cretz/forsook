package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class LiteralExpression extends Expression {

    private String value;
    private LiteralExpressionType type;
    
    public LiteralExpression() {
    }
    
    public LiteralExpression(String value, LiteralExpressionType type) {
        this.value = value;
        this.type = type;
    }
 
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public LiteralExpressionType getType() {
        return type;
    }
    
    public void setType(LiteralExpressionType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        LiteralExpression other = (LiteralExpression) obj;
        if (type != other.type) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    public static enum LiteralExpressionType {
        DECIMAL_INTEGER,
        HEX_INTEGER,
        OCTAL_INTEGER,
        BINARY_INTEGER,
        DECIMAL_FLOATING_POINT,
        HEXADECIMAL_FLOATING_POINT,
        BOOLEAN,
        CHARACTER,
        STRING,
        NULL
    }
 }
