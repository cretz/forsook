package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.packag.TypeDeclaration;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;

@JlsReference("8.9")
@SuppressWarnings("serial")
public class EnumDeclaration extends TypeDeclaration<EnumBody> {

    private List<ClassOrInterfaceType> implementsList;
    
    public EnumDeclaration() {
    }
    
    public EnumDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations,
            Identifier name, List<Modifier> modifiers, EnumBody body,
            List<ClassOrInterfaceType> implementsList) {
        super(javadoc, annotations, name, modifiers, body);
        this.implementsList = implementsList;
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
        result = prime * result
                + ((implementsList == null) ? 0 : implementsList.hashCode());
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
        EnumDeclaration other = (EnumDeclaration) obj;
        if (implementsList == null) {
            if (other.implementsList != null) {
                return false;
            }
        } else if (!implementsList.equals(other.implementsList)) {
            return false;
        }
        return true;
    }
}
