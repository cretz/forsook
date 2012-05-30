package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class TypeParameter extends JavaModel {

    private Identifier name;
    private List<ClassOrInterfaceType> typesBound;
    
    public TypeParameter() {
    }
    
    public TypeParameter(Identifier name, List<ClassOrInterfaceType> typesBound) {
        this.name = name;
        this.typesBound = typesBound;
    }
    
    public Identifier getName() {
        return name;
    }
    
    public void setName(Identifier name) {
        this.name = name;
    }
    
    public List<ClassOrInterfaceType> getTypesBound() {
        return typesBound;
    }
    
    public void setTypesBound(List<ClassOrInterfaceType> typesBound) {
        this.typesBound = typesBound;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((typesBound == null) ? 0 : typesBound.hashCode());
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
        TypeParameter other = (TypeParameter) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (typesBound == null) {
            if (other.typesBound != null) {
                return false;
            }
        } else if (!typesBound.equals(other.typesBound)) {
            return false;
        }
        return true;
    }
}
