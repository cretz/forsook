package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.TypeParameter;

@JlsReference("8.8")
@SuppressWarnings("serial")
public class ConstructorDeclaration extends BodyDeclaration {

    private List<Modifier> modifiers;
    private List<TypeParameter> typeParameters;
    private Identifier name;
    private List<Parameter> parameters;
    private List<ClassOrInterfaceType> throwsList;
    private BlockStatement block;
    
    public ConstructorDeclaration() {
    }

    public ConstructorDeclaration(JavadocComment javadoc, 
            List<AnnotationExpression> annotations, List<Modifier> modifiers,
            List<TypeParameter> typeParameters, Identifier name,
            List<Parameter> parameters, List<ClassOrInterfaceType> throwsList,
            BlockStatement block) {
        super(javadoc, annotations);
        this.modifiers = modifiers;
        this.typeParameters = typeParameters;
        this.name = name;
        this.parameters = parameters;
        this.throwsList = throwsList;
        this.block = block;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }
    
    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public List<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeParameter> typeParameters) {
        this.typeParameters = typeParameters;
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
                + ((modifiers == null) ? 0 : modifiers.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((parameters == null) ? 0 : parameters.hashCode());
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
        ConstructorDeclaration other = (ConstructorDeclaration) obj;
        if (block == null) {
            if (other.block != null) {
                return false;
            }
        } else if (!block.equals(other.block)) {
            return false;
        }
        if (modifiers == null) {
            if (other.modifiers != null) {
                return false;
            }
        } else if (!modifiers.equals(other.modifiers)) {
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
