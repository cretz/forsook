package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class SingleElementAnnotationExpression extends AnnotationExpression {

    private Expression value;
    
    public SingleElementAnnotationExpression() {
    }
    
    public SingleElementAnnotationExpression(String name, Expression value) {
        super(name);
        this.value = value;
    }
    
    public Expression getValue() {
        return value;
    }
    
    public void setValue(Expression value) {
        this.value = value;
    }
}
