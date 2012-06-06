package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("8.8.7.1")
@SuppressWarnings("serial")
public class ExplicitConstructorInvocationStatement extends Statement {

    private Expression scope;
    private NonWildTypeArguments typeArguments;
    private boolean thisPresent;
    private List<Expression> arguments;
    
    public ExplicitConstructorInvocationStatement() {
    }

    public ExplicitConstructorInvocationStatement(Expression scope,
            NonWildTypeArguments typeArguments, boolean thisPresent,
            List<Expression> arguments) {
        this.scope = scope;
        this.typeArguments = typeArguments;
        this.thisPresent = thisPresent;
        this.arguments = arguments;
    }

    public Expression getScope() {
        return scope;
    }

    public void setScope(Expression scope) {
        this.scope = scope;
    }

    public NonWildTypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(NonWildTypeArguments typeArguments) {
        this.typeArguments = typeArguments;
    }

    public boolean isThisPresent() {
        return thisPresent;
    }

    public void setThisPresent(boolean thisPresent) {
        this.thisPresent = thisPresent;
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
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + (thisPresent ? 1231 : 1237);
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
        ExplicitConstructorInvocationStatement other = (ExplicitConstructorInvocationStatement) obj;
        if (arguments == null) {
            if (other.arguments != null) {
                return false;
            }
        } else if (!arguments.equals(other.arguments)) {
            return false;
        }
        if (scope == null) {
            if (other.scope != null) {
                return false;
            }
        } else if (!scope.equals(other.scope)) {
            return false;
        }
        if (thisPresent != other.thisPresent) {
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
