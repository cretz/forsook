package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class ClassOrInterfaceType extends Type {

    private ClassOrInterfaceType previous;
    private String name;
    private List<? extends Type> typeArguments;
    
    public ClassOrInterfaceType() {
    }

    public ClassOrInterfaceType(ClassOrInterfaceType previous, String name,
            List<? extends Type> typeArguments) {
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

    public List<? extends Type> getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(List<? extends Type> typeArguments) {
        this.typeArguments = typeArguments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((previous == null) ? 0 : previous.hashCode());
        result = prime * result
                + ((typeArguments == null) ? 0 : typeArguments.hashCode());
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
        ClassOrInterfaceType other = (ClassOrInterfaceType) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (previous == null) {
            if (other.previous != null) {
                return false;
            }
        } else if (!previous.equals(other.previous)) {
            return false;
        }
        if (typeArguments == null) {
            if (other.typeArguments != null) {
                return false;
            }
        } else if (!typeArguments.equals(other.typeArguments)) {
            return false;
        }
        return true;
    }
}
