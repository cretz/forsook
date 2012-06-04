package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.JavaModel;

@JlsReference("9.7.1")
@SuppressWarnings("serial")
public class ElementValue extends JavaModel {

    private Expression value;
    
    public ElementValue() {
    }
    
    public ElementValue(Expression value) {
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
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        ElementValue other = (ElementValue) obj;
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
