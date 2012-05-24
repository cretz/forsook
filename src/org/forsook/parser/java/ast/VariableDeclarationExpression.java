package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class VariableDeclarationExpression extends Expression {

    private JavadocComment javadoc;
    private int modifiers;
    private List<AnnotationExpression> annotations;
    private Type type;
    private List<VariableDeclarator> variables;
    
    public VariableDeclarationExpression() {
    }

    public VariableDeclarationExpression(JavadocComment javadoc, int modifiers,
            List<AnnotationExpression> annotations, Type type,
            List<VariableDeclarator> variables) {
        this.javadoc = javadoc;
        this.modifiers = modifiers;
        this.annotations = annotations;
        this.type = type;
        this.variables = variables;
    }
    
    public JavadocComment getJavadoc() {
        return javadoc;
    }
    
    public void setJavadoc(JavadocComment javadoc) {
        this.javadoc = javadoc;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public List<AnnotationExpression> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationExpression> annotations) {
        this.annotations = annotations;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<VariableDeclarator> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableDeclarator> variables) {
        this.variables = variables;
    }

}
