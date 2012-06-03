package org.forsook.parser.java.ast.type;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("4.3")
@SuppressWarnings("serial")
public class ClassOrInterfaceType extends ReferenceType {

    private ClassOrInterfaceType previous;
    private Identifier name;
    private TypeArguments typeArguments;
    
    public ClassOrInterfaceType() {
    }

    public ClassOrInterfaceType(ClassOrInterfaceType previous, Identifier name,
            TypeArguments typeArguments) {
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

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }
    
    public void setTypeArguments(TypeArguments typeArguments) {
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
