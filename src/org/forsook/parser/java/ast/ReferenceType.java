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
}
