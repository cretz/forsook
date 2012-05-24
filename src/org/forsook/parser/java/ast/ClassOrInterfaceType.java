package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class ClassOrInterfaceType extends Type {

    private ClassOrInterfaceType previous;
    private String name;
    private List<Type> typeArguments;
    
    public ClassOrInterfaceType() {
    }

    public ClassOrInterfaceType(ClassOrInterfaceType previous, String name,
            List<Type> typeArguments) {
        this.previous = previous;
        this.name = name;
        this.typeArguments = typeArguments;
    }

    public ClassOrInterfaceType getPrevious() {
        return previous;
    }

    public void setPrevious(ClassOrInterfaceType previous) {
        this.previous = previous;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(List<Type> typeArguments) {
        this.typeArguments = typeArguments;
    }
}
