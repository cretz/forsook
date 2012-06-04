package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7.3")
@SuppressWarnings("serial")
public class SingleElementAnnotationExpression extends AnnotationExpression {

    private ElementValue value;
    
    public SingleElementAnnotationExpression() {
    }
    
    public SingleElementAnnotationExpression(QualifiedName name, ElementValue value) {
        super(name);
        this.value = value;
    }
    
    public ElementValue getValue() {
        return value;
    }
    
    public void setValue(ElementValue value) {
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
