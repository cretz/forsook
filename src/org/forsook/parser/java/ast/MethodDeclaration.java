package org.forsook.parser.java.ast;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.TypeParameter;

@JlsReference("8.4")
@SuppressWarnings("serial")
public class MethodDeclaration extends BodyDeclaration {

    private Modifier modifier;
    private List<TypeParameter> typeParameters;
    private Type result;
    private Identifier name;
    private List<Parameter> parameters;
    private List<ClassOrInterfaceType> throwsList;
    private BlockStatement block;

    public MethodDeclaration() {
    }

    public MethodDeclaration(JavadocComment javadoc,
            List<AnnotationExpression> annotations, Modifier modifier,
            List<TypeParameter> typeParameters, Type result, Identifier name,
            List<Parameter> parameters, List<ClassOrInterfaceType> throwsList,
            BlockStatement block) {
        this.modifier = modifier;
        this.typeParameters = typeParameters;
        this.result = result;
        this.name = name;
        this.parameters = parameters;
        this.throwsList = throwsList;
        this.block = block;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }

    public List<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeParameter> typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Type getResult() {
        return result;
    }

    public void setResult(Type result) {
        this.result = result;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<ClassOrInterfaceType> getThrowsList() {
        return throwsList;
    }

    public void setThrowsList(List<ClassOrInterfaceType> throwsList) {
        this.throwsList = throwsList;
    }

    public BlockStatement getBlock() {
        return block;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((block == null) ? 0 : block.hashCode());
        result = prime * result
                + ((modifier == null) ? 0 : modifier.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((parameters == null) ? 0 : parameters.hashCode());
        result = prime * result
                + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result
                + ((throwsList == null) ? 0 : throwsList.hashCode());
        result = prime * result
                + ((typeParameters == null) ? 0 : typeParameters.hashCode());
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
        MethodDeclaration other = (MethodDeclaration) obj;
        if (block == null) {
            if (other.block != null) {
                return false;
            }
        } else if (!block.equals(other.block)) {
            return false;
        }
        if (modifier == null) {
            if (other.modifier != null) {
                return false;
            }
        } else if (!modifier.equals(other.modifier)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (parameters == null) {
            if (other.parameters != null) {
                return false;
            }
        } else if (!parameters.equals(other.parameters)) {
            return false;
        }
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        } else if (!result.equals(other.result)) {
            return false;
        }
        if (throwsList == null) {
            if (other.throwsList != null) {
                return false;
            }
        } else if (!throwsList.equals(other.throwsList)) {
            return false;
        }
        if (typeParameters == null) {
            if (other.typeParameters != null) {
                return false;
            }
        } else if (!typeParameters.equals(other.typeParameters)) {
            return false;
        }
        return true;
    }
}
