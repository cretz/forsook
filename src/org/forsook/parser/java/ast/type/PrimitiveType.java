package org.forsook.parser.java.ast.type;

import org.forsook.parser.java.JlsReference;

@JlsReference("4.2")
@SuppressWarnings("serial")
public class PrimitiveType extends Type {

    private Primitive type;
    
    public PrimitiveType() {
    }
    
    public PrimitiveType(Primitive type) {
        this.type = type;
    }
    
    public Primitive getType() {
        return type;
    }
    
    public void setType(Primitive type) {
        this.type = type;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        PrimitiveType other = (PrimitiveType) obj;
        if (type != other.type) {
            return false;
        }
        return true;
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
