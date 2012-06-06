package org.forsook.parser.java.ast.expression;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.array.ArrayInitializerExpression;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("15.10")
@SuppressWarnings("serial")
public class ArrayCreationExpression extends Expression {

    private Type type;
    private List<Dimension> dimensions;
    private ArrayInitializerExpression initializer;
    
    public ArrayCreationExpression() {
    }

    public ArrayCreationExpression(Type type, List<Dimension> dimensions,
            ArrayInitializerExpression initializer) {
        this.type = type;
        this.dimensions = dimensions;
        this.initializer = initializer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public ArrayInitializerExpression getInitializer() {
        return initializer;
    }

    public void setInitializer(ArrayInitializerExpression initializer) {
        this.initializer = initializer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((dimensions == null) ? 0 : dimensions.hashCode());
        result = prime * result
                + ((initializer == null) ? 0 : initializer.hashCode());
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
        ArrayCreationExpression other = (ArrayCreationExpression) obj;
        if (dimensions == null) {
            if (other.dimensions != null) {
                return false;
            }
        } else if (!dimensions.equals(other.dimensions)) {
            return false;
        }
        if (initializer == null) {
            if (other.initializer != null) {
                return false;
            }
        } else if (!initializer.equals(other.initializer)) {
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
