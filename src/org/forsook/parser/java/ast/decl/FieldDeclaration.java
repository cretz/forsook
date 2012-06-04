package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("8.3")
@SuppressWarnings("serial")
public class FieldDeclaration extends BodyDeclaration {

    private Modifier modifier;
    private Type type;
    private List<VariableDeclarator> variables;
    
    public FieldDeclaration() {
    }
    
    public FieldDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations,
            Modifier modifier, Type type, List<VariableDeclarator> variables) {
        super(javadoc, annotations);
        this.modifier = modifier;
        this.type = type;
        this.variables = variables;
    }
    
    public Modifier getModifier() {
        return modifier;
    }
    
    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
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
        int result = super.hashCode();
        result = prime * result
                + ((modifier == null) ? 0 : modifier.hashCode());
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
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FieldDeclaration other = (FieldDeclaration) obj;
        if (modifier == null) {
            if (other.modifier != null) {
                return false;
            }
        } else if (!modifier.equals(other.modifier)) {
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
