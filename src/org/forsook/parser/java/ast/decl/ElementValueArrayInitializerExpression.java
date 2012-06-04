package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.ast.Expression;

@SuppressWarnings("serial")
public class ElementValueArrayInitializerExpression extends Expression {

    private List<Expression> values;
    
    public ElementValueArrayInitializerExpression() {
    }

    public ElementValueArrayInitializerExpression(List<Expression> values) {
        this.values = values;
    }

    public List<Expression> getValues() {
        return values;
    }
    
    public void setValues(List<Expression> values) {
        this.values = values;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((values == null) ? 0 : values.hashCode());
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
        ElementValueArrayInitializerExpression other = (ElementValueArrayInitializerExpression) obj;
        if (values == null) {
            if (other.values != null) {
                return false;
            }
        } else if (!values.equals(other.values)) {
            return false;
        }
        return true;
    }
}
