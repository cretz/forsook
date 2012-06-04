package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.ast.JavaModel;

@SuppressWarnings("serial")
public abstract class TypeBody extends JavaModel {

    private List<BodyDeclaration> members;
    
    public TypeBody() {
    }
    
    public TypeBody(List<BodyDeclaration> members) {
        this.members = members;
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
        int result = 1;
        result = prime * result + ((members == null) ? 0 : members.hashCode());
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
        TypeBody other = (TypeBody) obj;
        if (members == null) {
            if (other.members != null) {
                return false;
            }
        } else if (!members.equals(other.members)) {
            return false;
        }
        return true;
    }
}
