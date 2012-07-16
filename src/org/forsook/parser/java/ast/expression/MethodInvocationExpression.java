package org.forsook.parser.java.ast.expression;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.NonWildTypeArguments;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.statement.StatementExpression;

@JlsReference("15.12")
@SuppressWarnings("serial")
public class MethodInvocationExpression extends Expression
        implements PrimaryNoNewArrayExpression, StatementExpression, ScopedExpression {

    private Expression scope;
    private QualifiedName className;
    private boolean superPresent;
    private NonWildTypeArguments typeArguments;
    private QualifiedName methodName;
    private List<Expression> arguments;
    
    public MethodInvocationExpression() {
    }

    public MethodInvocationExpression(Expression scope,
            QualifiedName className, boolean superPresent,
            NonWildTypeArguments typeArguments, QualifiedName methodName,
            List<Expression> arguments) {
        this.scope = scope;
        this.className = className;
        this.superPresent = superPresent;
        this.typeArguments = typeArguments;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public Expression getScope() {
        return scope;
    }

    @Override
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

    public NonWildTypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(NonWildTypeArguments typeArguments) {
        this.typeArguments = typeArguments;
    }

    public QualifiedName getMethodName() {
        return methodName;
    }

    public void setMethodName(QualifiedName methodName) {
        this.methodName = methodName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((arguments == null) ? 0 : arguments.hashCode());
        result = prime * result
                + ((className == null) ? 0 : className.hashCode());
        result = prime * result
                + ((methodName == null) ? 0 : methodName.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + (superPresent ? 1231 : 1237);
        result = prime * result
                + ((typeArguments == null) ? 0 : typeArguments.hashCode());
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
        MethodInvocationExpression other = (MethodInvocationExpression) obj;
        if (arguments == null) {
            if (other.arguments != null) {
                return false;
            }
        } else if (!arguments.equals(other.arguments)) {
            return false;
        }
        if (className == null) {
            if (other.className != null) {
                return false;
            }
        } else if (!className.equals(other.className)) {
            return false;
        }
        if (methodName == null) {
            if (other.methodName != null) {
                return false;
            }
        } else if (!methodName.equals(other.methodName)) {
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
        if (typeArguments == null) {
            if (other.typeArguments != null) {
                return false;
            }
        } else if (!typeArguments.equals(other.typeArguments)) {
            return false;
        }
        return true;
    }
}
