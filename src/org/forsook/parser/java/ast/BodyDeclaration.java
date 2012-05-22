package org.forsook.parser.java.ast;

import java.util.List;

public abstract class BodyDeclaration {

	private String javadoc;
	private List<AnnotationExpression> annotations;
	
	public BodyDeclaration() {
	}

	public BodyDeclaration(String javadoc, List<AnnotationExpression> annotations) {
		this.javadoc = javadoc;
		this.annotations = annotations;
	}

	public String getJavadoc() {
		return javadoc;
	}

	public void setJavadoc(String javadoc) {
		this.javadoc = javadoc;
	}

	public List<AnnotationExpression> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationExpression> annotations) {
		this.annotations = annotations;
	}

}
