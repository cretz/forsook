package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("14.20.3")
@SuppressWarnings("serial")
public class Resource extends JavaModel {

    private List<AnnotationExpression> annotations;
    private boolean finalPresent;
    private Type type;
    private VariableDeclaratorId name;
    private Expression expression;
    
    public Resource() {
    }

    public Resource(List<AnnotationExpression> annotations, boolean finalPresent, 
            Type type, VariableDeclaratorId name, Expression expression) {
        this.annotations = annotations;
        this.finalPresent = finalPresent;
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    public List<AnnotationExpression> getAnnotations() {
        return annotations;
    }
    
    public void setAnnotations(List<AnnotationExpression> annotations) {
        this.annotations = annotations;
    }
    
    public boolean isFinalPresent() {
        return finalPresent;
    }
    
    public void setFinalPresent(boolean finalPresent) {
        this.finalPresent = finalPresent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public VariableDeclaratorId getName() {
        return name;
    }

    public void setName(VariableDeclaratorId name) {
        this.name = name;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result
                + ((expression == null) ? 0 : expression.hashCode());
        result = prime * result + (finalPresent ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Resource other = (Resource) obj;
        if (annotations == null) {
            if (other.annotations != null) {
                return false;
            }
        } else if (!annotations.equals(other.annotations)) {
            return false;
        }
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        if (finalPresent != other.finalPresent) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
