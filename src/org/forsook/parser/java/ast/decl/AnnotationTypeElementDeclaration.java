package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("9.6.1")
@SuppressWarnings("serial")
public class AnnotationTypeElementDeclaration extends BodyDeclaration {

    private Modifier modifiers;
    private Type type;
    private Identifier name;
    private ElementValue defaultValue;
    
    public AnnotationTypeElementDeclaration() {
    }

    public AnnotationTypeElementDeclaration(JavadocComment javadoc, 
            List<AnnotationExpression> annotations, Modifier modifiers, Type type,
            Identifier name, ElementValue defaultValue) {
        super(javadoc, annotations);
        this.modifiers = modifiers;
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public Modifier getModifiers() {
        return modifiers;
    }

    public void setModifiers(Modifier modifiers) {
        this.modifiers = modifiers;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public ElementValue getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(ElementValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((defaultValue == null) ? 0 : defaultValue.hashCode());
        result = prime * result
                + ((modifiers == null) ? 0 : modifiers.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        AnnotationTypeElementDeclaration other = (AnnotationTypeElementDeclaration) obj;
        if (defaultValue == null) {
            if (other.defaultValue != null) {
                return false;
            }
        } else if (!defaultValue.equals(other.defaultValue)) {
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
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
