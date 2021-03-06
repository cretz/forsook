package org.forsook.parser.java.ast.packag;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("7.4")
@SuppressWarnings("serial")
public class PackageDeclaration extends JavaModel {

    private List<AnnotationExpression> annotations;
    private QualifiedName name;
    
    public PackageDeclaration() {
        
    }
    
    public PackageDeclaration(List<AnnotationExpression> annotations, QualifiedName name) {
        this.annotations = annotations;
        this.name = name;
    }
    
    public List<AnnotationExpression> getAnnotations() {
        return annotations;
    }
    
    public void setAnnotations(List<AnnotationExpression> annotations) {
        this.annotations = annotations;
    }
    
    public QualifiedName getName() {
        return name;
    }
    
    public void setName(QualifiedName name) {
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
