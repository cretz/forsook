package org.forsook.parser.java.ast.type;

import org.forsook.parser.java.JlsReference;

@JlsReference("4.5.1")
@SuppressWarnings("serial")
public class WildcardType extends Type {
    
    private ReferenceType extendsType;
    private ReferenceType superType;
    
    public WildcardType() {
    }
    
    public WildcardType(ReferenceType extendsType, ReferenceType superType) {
        this.extendsType = extendsType;
        this.superType = superType;
    }
    
    public ReferenceType getExtendsType() {
        return extendsType;
    }
    
    public void setExtendsType(ReferenceType extendsType) {
        this.extendsType = extendsType;
    }
    
    public ReferenceType getSuperType() {
        return superType;
    }
    
    public void setSuperType(ReferenceType superType) {
        this.superType = superType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((extendsType == null) ? 0 : extendsType.hashCode());
        result = prime * result
                + ((superType == null) ? 0 : superType.hashCode());
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
        WildcardType other = (WildcardType) obj;
        if (extendsType == null) {
            if (other.extendsType != null) {
                return false;
            }
        } else if (!extendsType.equals(other.extendsType)) {
            return false;
        }
        if (superType == null) {
            if (other.superType != null) {
                return false;
            }
        } else if (!superType.equals(other.superType)) {
            return false;
        }
        return true;
    }
}
