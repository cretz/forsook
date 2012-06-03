package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.11")
@SuppressWarnings("serial")
public class FieldAccessExpression extends Expression {

    private Expression scope;
    private QualifiedName superName;
    private Identifier field;
    
    public FieldAccessExpression() {
    }
    
    public FieldAccessExpression(Expression scope, QualifiedName superName, Identifier field) {
        this.scope = scope;
        this.superName = superName;
        this.field = field;
    }
    
    public Expression getScope() {
        return scope;
    }
    
    public void setScope(Expression scope) {
        this.scope = scope;
    }
    
    public QualifiedName getSuperName() {
        return superName;
    }
    
    public void setSuperName(QualifiedName superName) {
        this.superName = superName;
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
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result
                + ((superName == null) ? 0 : superName.hashCode());
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
        if (superName == null) {
            if (other.superName != null) {
                return false;
            }
        } else if (!superName.equals(other.superName)) {
            return false;
        }
        return true;
    }
    
    
}
