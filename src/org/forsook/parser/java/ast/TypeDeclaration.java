package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public abstract class TypeDeclaration extends BodyDeclaration {
	
	private String name;
    private int modifiers;
    private List<BodyDeclaration> members;
    
    public TypeDeclaration() {
    	
    }
    
	public TypeDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations, String name, 
			int modifiers, List<BodyDeclaration> members) {
		super(javadoc, annotations);
		this.name = name;
		this.modifiers = modifiers;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public List<BodyDeclaration> getMembers() {
		return members;
	}

	public void setMembers(List<BodyDeclaration> members) {
		this.members = members;
	}
}
