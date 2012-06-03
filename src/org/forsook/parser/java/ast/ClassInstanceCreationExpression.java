package org.forsook.parser.java.ast;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;

@JlsReference("15.9")
@SuppressWarnings("serial")
public class ClassInstanceCreationExpression extends Expression {

    private Expression scope;
    private TypeArguments typeArguments;
    private ClassOrInterfaceType type;
    private boolean diamond;
    private List<Expression> arguments;
    private List<BodyDeclaration> anonymousDeclarations;
    
    public ClassInstanceCreationExpression() {
    }
    
    public ClassInstanceCreationExpression(Expression scope,
            TypeArguments typeArguments, ClassOrInterfaceType type,
            boolean diamond, List<Expression> arguments,
            List<BodyDeclaration> anonymousDeclarations) {
        this.scope = scope;
        this.typeArguments = typeArguments;
        this.type = type;
        this.diamond = diamond;
        this.arguments = arguments;
        this.anonymousDeclarations = anonymousDeclarations;
    }

    public Expression getScope() {
        return scope;
    }

    public void setScope(Expression scope) {
        this.scope = scope;
    }

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }
    
    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
    }

    public ClassOrInterfaceType getType() {
        return type;
    }

    public void setType(ClassOrInterfaceType type) {
        this.type = type;
    }
    
    public boolean isDiamond() {
        return diamond;
    }
    
    public void setDiamond(boolean diamond) {
        this.diamond = diamond;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    public List<BodyDeclaration> getAnonymousDeclarations() {
        return anonymousDeclarations;
    }

    public void setAnonymousDeclarations(List<BodyDeclaration> anonymousDeclarations) {
        this.anonymousDeclarations = anonymousDeclarations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((anonymousDeclarations == null) ? 0 : anonymousDeclarations
                        .hashCode());
        result = prime * result
                + ((arguments == null) ? 0 : arguments.hashCode());
        result = prime * result + (diamond ? 1231 : 1237);
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        ClassInstanceCreationExpression other = (ClassInstanceCreationExpression) obj;
        if (anonymousDeclarations == null) {
            if (other.anonymousDeclarations != null) {
                return false;
            }
        } else if (!anonymousDeclarations.equals(other.anonymousDeclarations)) {
            return false;
        }
        if (arguments == null) {
            if (other.arguments != null) {
                return false;
            }
        } else if (!arguments.equals(other.arguments)) {
            return false;
        }
        if (diamond != other.diamond) {
            return false;
        }
        if (scope == null) {
            if (other.scope != null) {
                return false;
            }
        } else if (!scope.equals(other.scope)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
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
