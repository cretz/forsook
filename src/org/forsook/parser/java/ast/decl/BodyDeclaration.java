package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.lexical.JavadocComment;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result + ((javadoc == null) ? 0 : javadoc.hashCode());
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
        BodyDeclaration other = (BodyDeclaration) obj;
        if (annotations == null) {
            if (other.annotations != null) {
                return false;
            }
        } else if (!annotations.equals(other.annotations)) {
            return false;
        }
        if (javadoc == null) {
            if (other.javadoc != null) {
                return false;
            }
        } else if (!javadoc.equals(other.javadoc)) {
            return false;
        }
        return true;
    }
}
