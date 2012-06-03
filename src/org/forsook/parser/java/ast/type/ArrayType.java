package org.forsook.parser.java.ast.type;

@SuppressWarnings("serial")
public class ArrayType extends ReferenceType {

    private Type type;
    
    public ArrayType(Type type) {
        this.type = type;
    }
}
