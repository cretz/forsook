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
