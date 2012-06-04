package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7")
@SuppressWarnings("serial")
public abstract class AnnotationExpression extends Expression {

    private QualifiedName name;
    
    public AnnotationExpression() {
    }
    
    public AnnotationExpression(QualifiedName name) {
        this.name = name;
    }
    
    public QualifiedName getName() {
        return name;
    }
    
    public void setName(QualifiedName name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        AnnotationExpression other = (AnnotationExpression) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
