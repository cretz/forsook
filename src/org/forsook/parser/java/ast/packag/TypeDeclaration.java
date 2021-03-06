package org.forsook.parser.java.ast.packag;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.TypeBody;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;

@JlsReference("7.6")
@SuppressWarnings("serial")
public abstract class TypeDeclaration<T extends TypeBody> extends BodyDeclaration {
    
    private Identifier name;
    private List<Modifier> modifiers;
    private T body;
    
    public TypeDeclaration() {
    }
    
    public TypeDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations,
            Identifier name, List<Modifier> modifiers, T members) {
        super(javadoc, annotations);
        this.name = name;
        this.modifiers = modifiers;
        this.body = members;
    }

    public Identifier getName() {
        return name;
    }
    
    public void setName(Identifier name) {
        this.name = name;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public T getBody() {
        return body;
    }
    
    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result
                + ((modifiers == null) ? 0 : modifiers.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("rawtypes")
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
        TypeDeclaration other = (TypeDeclaration) obj;
        if (body == null) {
            if (other.body != null) {
                return false;
            }
        } else if (!body.equals(other.body)) {
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
        return true;
    }
}
