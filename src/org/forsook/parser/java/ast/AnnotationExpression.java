package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public abstract class AnnotationExpression extends Expression {

    private String name;
    
    public AnnotationExpression() {
    }
    
    public AnnotationExpression(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
