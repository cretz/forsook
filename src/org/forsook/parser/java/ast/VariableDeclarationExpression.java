package org.forsook.parser.java.ast;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("14.4")
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result + ((javadoc == null) ? 0 : javadoc.hashCode());
        result = prime * result + modifiers;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result
                + ((variables == null) ? 0 : variables.hashCode());
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
        VariableDeclarationExpression other = (VariableDeclarationExpression) obj;
        if (annotations == null) {
            if (other.annotations != null) {
                return false;
            }
        } else if (!annotations.equals(other.annotations)) {
            return false;
        }
        if (javadoc == null) {
            if (other.javadoc != null) {
                return false;
            }
        } else if (!javadoc.equals(other.javadoc)) {
            return false;
        }
        if (modifiers != other.modifiers) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        if (variables == null) {
            if (other.variables != null) {
                return false;
            }
        } else if (!variables.equals(other.variables)) {
            return false;
        }
        return true;
    }
}
