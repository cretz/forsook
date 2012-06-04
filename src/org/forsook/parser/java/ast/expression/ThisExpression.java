package org.forsook.parser.java.ast.expression;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.8.3")
@SuppressWarnings("serial")
public class ThisExpression extends Expression implements PrimaryNoNewArrayExpression {

    private QualifiedName name;
    
    public ThisExpression() {
    }
    
    public ThisExpression(QualifiedName name) {
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
        ThisExpression other = (ThisExpression) obj;
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
