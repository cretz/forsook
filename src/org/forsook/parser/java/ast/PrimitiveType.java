package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class PrimitiveType extends Type {

    private PrimitiveType type;
    
    public PrimitiveType() {
    }
    
    public PrimitiveType(PrimitiveType type) {
        this.type = type;
    }
    
    public PrimitiveType getType() {
        return type;
    }
    
    public void setType(PrimitiveType type) {
        this.type = type;
    }
    
    public static enum Primitive {
        BOOLEAN,
        BYTE,
        CHAR,
        DOUBLE,
        FLOAT,
        INT,
        LONG,
        SHORT
    }
}
