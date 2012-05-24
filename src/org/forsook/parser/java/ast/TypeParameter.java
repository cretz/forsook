package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class TypeParameter extends JavaModel {

    private String name;
    private List<ClassOrInterfaceType> typesBound;
    
    public TypeParameter() {
    }
    
    public TypeParameter(String name, List<ClassOrInterfaceType> typesBound) {
        this.name = name;
        this.typesBound = typesBound;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<ClassOrInterfaceType> getTypesBound() {
        return typesBound;
    }
    
    public void setTypesBound(List<ClassOrInterfaceType> typesBound) {
        this.typesBound = typesBound;
    }
}
