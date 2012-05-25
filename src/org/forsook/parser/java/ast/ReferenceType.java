package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class ReferenceType extends Type {

    private Type type;
    private int arrayCount;
    
    public ReferenceType() {   
    }
    
    public ReferenceType(Type type, int arrayCount) {
        this.type = type;
        this.arrayCount = arrayCount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getArrayCount() {
        return arrayCount;
    }

    public void setArrayCount(int arrayCount) {
        this.arrayCount = arrayCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + arrayCount;
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
        ReferenceType other = (ReferenceType) obj;
        if (arrayCount != other.arrayCount) {
            return false;
        }
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
