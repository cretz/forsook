package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("9.7.3")
@SuppressWarnings("serial")
public class SingleElementAnnotationExpression extends AnnotationExpression {

    private Expression value;
    
    public SingleElementAnnotationExpression() {
    }
    
    public SingleElementAnnotationExpression(QualifiedName name, Expression value) {
        super(name);
        this.value = value;
    }
    
    public Expression getValue() {
        return value;
    }
    
    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        SingleElementAnnotationExpression other = (SingleElementAnnotationExpression) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
