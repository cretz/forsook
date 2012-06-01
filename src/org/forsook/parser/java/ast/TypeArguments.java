package org.forsook.parser.java.ast;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("4.5.1")
@SuppressWarnings("serial")
public class TypeArguments extends JavaModel {

    private List<Type> types;
    
    public TypeArguments() {
    }
    
    public TypeArguments(List<Type> types) {
        this.types = types;
    }
    
    public List<Type> getTypes() {
        return types;
    }
    
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((types == null) ? 0 : types.hashCode());
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
        TypeArguments other = (TypeArguments) obj;
        if (types == null) {
            if (other.types != null) {
                return false;
            }
        } else if (!types.equals(other.types)) {
            return false;
        }
        return true;
    }
}
