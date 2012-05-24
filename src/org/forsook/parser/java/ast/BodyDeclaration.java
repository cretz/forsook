package org.forsook.parser.java.ast;

import java.util.List;

@SuppressWarnings("serial")
public abstract class BodyDeclaration extends JavaModel {

	private JavadocComment javadoc;
	private List<AnnotationExpression> annotations;
	
	public BodyDeclaration() {
	}

	public BodyDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations) {
		this.javadoc = javadoc;
		this.annotations = annotations;
	}

	public JavadocComment getJavadoc() {
		return javadoc;
	}

	public void setJavadoc(JavadocComment javadoc) {
		this.javadoc = javadoc;
	}

	public List<AnnotationExpression> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationExpression> annotations) {
		this.annotations = annotations;
	}

}
