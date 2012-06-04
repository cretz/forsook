package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("8.9.1")
@SuppressWarnings("serial")
public class EnumConstantDeclaration extends BodyDeclaration {

    private Identifier name;
    private Expression arguments;
    private List<BodyDeclaration> members;
    
    public EnumConstantDeclaration() {
    }

    public EnumConstantDeclaration(Identifier name, Expression arguments,
            List<BodyDeclaration> members) {
        this.name = name;
        this.arguments = arguments;
        this.members = members;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Expression getArguments() {
        return arguments;
    }

    public void setArguments(Expression arguments) {
        this.arguments = arguments;
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
        result = prime * result
                + ((arguments == null) ? 0 : arguments.hashCode());
        result = prime * result + ((members == null) ? 0 : members.hashCode());
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
        EnumConstantDeclaration other = (EnumConstantDeclaration) obj;
        if (arguments == null) {
            if (other.arguments != null) {
                return false;
            }
        } else if (!arguments.equals(other.arguments)) {
            return false;
        }
        if (members == null) {
            if (other.members != null) {
                return false;
            }
        } else if (!members.equals(other.members)) {
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
