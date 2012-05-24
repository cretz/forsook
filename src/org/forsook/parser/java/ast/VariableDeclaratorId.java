package org.forsook.parser.java.ast;


@SuppressWarnings("serial")
public class VariableDeclaratorId extends JavaModel {

    private String name;
    private int arrayCount;
    
    public VariableDeclaratorId() {
    }
    
    public VariableDeclaratorId(String name, int arrayCount) {
        this.name = name;
        this.arrayCount = arrayCount;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getArrayCount() {
        return arrayCount;
    }
    
    public void setArrayCount(int arrayCount) {
        this.arrayCount = arrayCount;
    }
}
