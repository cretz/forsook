package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public class ClassOrInterfaceDeclaration extends TypeDeclaration {

    private boolean _interface;
    private List<TypeParameter> typeParameters;
    private List<ClassOrInterfaceType> extendsList;
    private List<ClassOrInterfaceType> implementsList;
    
    public ClassOrInterfaceDeclaration() {
    }
    
    public ClassOrInterfaceDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations,
            String name, int modifiers, List<BodyDeclaration> members,
            boolean _interface, List<TypeParameter> typeParameters,
            List<ClassOrInterfaceType> extendsList, List<ClassOrInterfaceType> implementsList) {
        super(javadoc, annotations, name, modifiers, members);
        this._interface = _interface;
        this.typeParameters = typeParameters;
        this.extendsList = extendsList;
        this.implementsList = implementsList;
    }

    public boolean isInterface() {
        return _interface;
    }

    public void setInterface(boolean _interface) {
        this._interface = _interface;
    }

    public List<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeParameter> typeParameters) {
        this.typeParameters = typeParameters;
    }

    public List<ClassOrInterfaceType> getExtendsList() {
        return extendsList;
    }

    public void setExtendsList(List<ClassOrInterfaceType> extendsList) {
        this.extendsList = extendsList;
    }

    public List<ClassOrInterfaceType> getImplementsList() {
        return implementsList;
    }

    public void setImplementsList(List<ClassOrInterfaceType> implementsList) {
        this.implementsList = implementsList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (_interface ? 1231 : 1237);
        result = prime * result
                + ((extendsList == null) ? 0 : extendsList.hashCode());
        result = prime * result
                + ((implementsList == null) ? 0 : implementsList.hashCode());
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
        ClassOrInterfaceDeclaration other = (ClassOrInterfaceDeclaration) obj;
        if (_interface != other._interface) {
            return false;
        }
        if (extendsList == null) {
            if (other.extendsList != null) {
                return false;
            }
        } else if (!extendsList.equals(other.extendsList)) {
            return false;
        }
        if (implementsList == null) {
            if (other.implementsList != null) {
                return false;
            }
        } else if (!implementsList.equals(other.implementsList)) {
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
