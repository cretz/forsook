package org.forsook.parser.java.ast;

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
}
