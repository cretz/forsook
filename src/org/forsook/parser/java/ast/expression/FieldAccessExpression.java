package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.11")
@SuppressWarnings("serial")
public class FieldAccessExpression extends Expression {

    private Expression scope;
    private QualifiedName className;
    private boolean superPresent;
    private Identifier field;
    
    public FieldAccessExpression() {
    }

    public FieldAccessExpression(Expression scope, QualifiedName className,
            boolean superPresent, Identifier field) {
        this.scope = scope;
        this.className = className;
        this.superPresent = superPresent;
        this.field = field;
    }

    public Expression getScope() {
        return scope;
    }

    public void setScope(Expression scope) {
        this.scope = scope;
    }

    public QualifiedName getClassName() {
        return className;
    }

    public void setClassName(QualifiedName className) {
        this.className = className;
    }

    public boolean isSuperPresent() {
        return superPresent;
    }

    public void setSuperPresent(boolean superPresent) {
        this.superPresent = superPresent;
    }

    public Identifier getField() {
        return field;
    }

    public void setField(Identifier field) {
        this.field = field;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((className == null) ? 0 : className.hashCode());
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + (superPresent ? 1231 : 1237);
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
        FieldAccessExpression other = (FieldAccessExpression) obj;
        if (className == null) {
            if (other.className != null) {
                return false;
            }
        } else if (!className.equals(other.className)) {
            return false;
        }
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        if (scope == null) {
            if (other.scope != null) {
                return false;
            }
        } else if (!scope.equals(other.scope)) {
            return false;
        }
        if (superPresent != other.superPresent) {
            return false;
        }
        return true;
    }
}
