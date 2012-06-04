package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("14.4")
@SuppressWarnings("serial")
public class LocalVariableDeclarationExpression extends Expression {

    private boolean _final;
    private List<AnnotationExpression> annotations;
    private Type type;
    private List<VariableDeclarator> variables;
    
    public LocalVariableDeclarationExpression() {
    }

    public LocalVariableDeclarationExpression(boolean _final,
            List<AnnotationExpression> annotations, Type type,
            List<VariableDeclarator> variables) {
        this._final = _final;
        this.annotations = annotations;
        this.type = type;
        this.variables = variables;
    }
    
    public boolean isFinal() {
        return _final;
    }
    
    public void setFinal(boolean _final) {
        this._final = _final;
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
        result = prime * result + (_final ? 1231 : 1237);
        result = prime * result
                + ((annotations == null) ? 0 : annotations.hashCode());
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
        LocalVariableDeclarationExpression other = (LocalVariableDeclarationExpression) obj;
        if (_final != other._final) {
            return false;
        }
        if (annotations == null) {
            if (other.annotations != null) {
                return false;
            }
        } else if (!annotations.equals(other.annotations)) {
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
