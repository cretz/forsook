package org.forsook.parser.java.ast.array;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("10.6")
@SuppressWarnings("serial")
public class ArrayInitializerExpression extends Expression {

    private List<Expression> values;
    private boolean trailingCommaPresent;

    public ArrayInitializerExpression() {
    }

    public ArrayInitializerExpression(List<Expression> values, boolean trailingCommaPresent) {
        this.values = values;
        this.trailingCommaPresent = trailingCommaPresent;
    }

    public List<Expression> getValues() {
        return values;
    }
    
    public void setValues(List<Expression> values) {
        this.values = values;
    }
    
    public boolean isTrailingCommaPresent() {
        return trailingCommaPresent;
    }
    
    public void setTrailingCommaPresent(boolean trailingCommaPresent) {
        this.trailingCommaPresent = trailingCommaPresent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (trailingCommaPresent ? 1231 : 1237);
        result = prime * result + ((values == null) ? 0 : values.hashCode());
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
        ArrayInitializerExpression other = (ArrayInitializerExpression) obj;
        if (trailingCommaPresent != other.trailingCommaPresent) {
            return false;
        }
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
