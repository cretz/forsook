package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("14.20.3")
@SuppressWarnings("serial")
public class Resource extends JavaModel {

    private Modifier modifiers;
    private Type type;
    private VariableDeclaratorId name;
    private Expression expression;
    
    public Resource() {
    }

    public Resource(Modifier modifiers, Type type, VariableDeclaratorId name,
            Expression expression) {
        this.modifiers = modifiers;
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    public Modifier getModifiers() {
        return modifiers;
    }

    public void setModifiers(Modifier modifiers) {
        this.modifiers = modifiers;
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
                + ((expression == null) ? 0 : expression.hashCode());
        result = prime * result
                + ((modifiers == null) ? 0 : modifiers.hashCode());
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
        if (expression == null) {
            if (other.expression != null) {
                return false;
            }
        } else if (!expression.equals(other.expression)) {
            return false;
        }
        if (modifiers == null) {
            if (other.modifiers != null) {
                return false;
            }
        } else if (!modifiers.equals(other.modifiers)) {
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
