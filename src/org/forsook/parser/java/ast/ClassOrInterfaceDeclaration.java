package org.forsook.parser.java.ast;

import java.util.List;

public class ClassOrInterfaceDeclaration extends TypeDeclaration {

    private boolean _interface;
    private List<TypeParameter> typeParameters;
    private List<ClassOrInterfaceType> extendsList;
    private List<ClassOrInterfaceType> implementsList;
    
    public ClassOrInterfaceDeclaration() {
    }
    
	public ClassOrInterfaceDeclaration(String javadoc, List<AnnotationExpression> annotations,
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
}
