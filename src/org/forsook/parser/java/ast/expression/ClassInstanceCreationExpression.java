package org.forsook.parser.java.ast.expression;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.statement.StatementExpression;
import org.forsook.parser.java.ast.type.TypeArguments;

@JlsReference("15.9")
@SuppressWarnings("serial")
public class ClassInstanceCreationExpression extends Expression 
        implements PrimaryNoNewArrayExpression, StatementExpression, ScopedExpression {

    private Expression scope;
    private TypeArguments preTypeArguments;
    private QualifiedName name;
    private TypeArguments postTypeArguments;
    private boolean diamond;
    private List<Expression> arguments;
    private ClassOrInterfaceBody anonymousBody;
    
    public ClassInstanceCreationExpression() {
    }

    public ClassInstanceCreationExpression(Expression scope,
            TypeArguments preTypeArguments, QualifiedName name,
            TypeArguments postTypeArguments, boolean diamond,
            List<Expression> arguments, ClassOrInterfaceBody anonymousBody) {
        this.scope = scope;
        this.preTypeArguments = preTypeArguments;
        this.name = name;
        this.postTypeArguments = postTypeArguments;
        this.diamond = diamond;
        this.arguments = arguments;
        this.anonymousBody = anonymousBody;
    }

    @Override
    public Expression getScope() {
        return scope;
    }

    @Override
    public void setScope(Expression scope) {
        this.scope = scope;
    }

    public TypeArguments getPreTypeArguments() {
        return preTypeArguments;
    }

    public void setPreTypeArguments(TypeArguments preTypeArguments) {
        this.preTypeArguments = preTypeArguments;
    }

    public QualifiedName getName() {
        return name;
    }

    public void setName(QualifiedName name) {
        this.name = name;
    }

    public TypeArguments getPostTypeArguments() {
        return postTypeArguments;
    }

    public void setPostTypeArguments(TypeArguments postTypeArguments) {
        this.postTypeArguments = postTypeArguments;
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

    public ClassOrInterfaceBody getAnonymousBody() {
        return anonymousBody;
    }

    public void setAnonymousBody(ClassOrInterfaceBody anonymousBody) {
        this.anonymousBody = anonymousBody;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((anonymousBody == null) ? 0 : anonymousBody.hashCode());
        result = prime * result
                + ((arguments == null) ? 0 : arguments.hashCode());
        result = prime * result + (diamond ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime
                * result
                + ((postTypeArguments == null) ? 0 : postTypeArguments
                        .hashCode());
        result = prime
                * result
                + ((preTypeArguments == null) ? 0 : preTypeArguments.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
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
        if (anonymousBody == null) {
            if (other.anonymousBody != null) {
                return false;
            }
        } else if (!anonymousBody.equals(other.anonymousBody)) {
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
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (postTypeArguments == null) {
            if (other.postTypeArguments != null) {
                return false;
            }
        } else if (!postTypeArguments.equals(other.postTypeArguments)) {
            return false;
        }
        if (preTypeArguments == null) {
            if (other.preTypeArguments != null) {
                return false;
            }
        } else if (!preTypeArguments.equals(other.preTypeArguments)) {
            return false;
        }
        if (scope == null) {
            if (other.scope != null) {
                return false;
            }
        } else if (!scope.equals(other.scope)) {
            return false;
        }
        return true;
    }
}
