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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((members == null) ? 0 : members.hashCode());
        result = prime * result + modifiers;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        TypeDeclaration other = (TypeDeclaration) obj;
        if (members == null) {
            if (other.members != null) {
                return false;
            }
        } else if (!members.equals(other.members)) {
            return false;
        }
        if (modifiers != other.modifiers) {
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
