package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("8.4.1")
@SuppressWarnings("serial")
public class Parameter extends JavaModel {

    private boolean finalPresent;
    private List<AnnotationExpression> annotations;
    private Type type;
    private boolean varArgs;
    private VariableDeclaratorId name;
    
    public Parameter() {
    }

    public Parameter(boolean finalPresent, List<AnnotationExpression> annotations,
            Type type, boolean varArgs, VariableDeclaratorId name) {
        this.finalPresent = finalPresent;
        this.annotations = annotations;
        this.type = type;
        this.varArgs = varArgs;
        this.name = name;
    }

    public boolean isFinalPresent() {
        return finalPresent;
    }
    
    public void setFinalPresent(boolean finalPresent) {
        this.finalPresent = finalPresent;
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

    public boolean isVarArgs() {
        return varArgs;
    }

    public void setVarArgs(boolean varArgs) {
        this.varArgs = varArgs;
    }

    public VariableDeclaratorId getName() {
        return name;
    }

    public void setName(VariableDeclaratorId name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result + (finalPresent ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + (varArgs ? 1231 : 1237);
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
        Parameter other = (Parameter) obj;
        if (annotations == null) {
            if (other.annotations != null) {
                return false;
            }
        } else if (!annotations.equals(other.annotations)) {
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
        if (varArgs != other.varArgs) {
            return false;
        }
        return true;
    }
}
