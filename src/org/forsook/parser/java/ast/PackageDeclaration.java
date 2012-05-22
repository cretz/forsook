package org.forsook.parser.java.ast;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PackageDeclaration implements Serializable {

	private List<AnnotationExpression> annotations;
	private String name;
	
	public PackageDeclaration() {
		
	}
	
	public PackageDeclaration(List<AnnotationExpression> annotations, String name) {
		this.annotations = annotations;
		this.name = name;
	}
	
	public List<AnnotationExpression> getAnnotations() {
		return annotations;
	}
	
	public void setAnnotations(List<AnnotationExpression> annotations) {
		this.annotations = annotations;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PackageDeclaration other = (PackageDeclaration) obj;
		if (annotations == null) {
			if (other.annotations != null) {
				return false;
			}
		} else if (!annotations.equals(other.annotations)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
