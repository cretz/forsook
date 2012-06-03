package org.forsook.parser.java.ast.type;

import org.forsook.parser.java.JlsReference;

@JlsReference("4.3")
@SuppressWarnings("serial")
public class ArrayType extends ReferenceType {

    private Type type;
    
    public ArrayType() {
    }
    
    public ArrayType(Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
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
        ArrayType other = (ArrayType) obj;
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
