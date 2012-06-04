package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("8.9")
@SuppressWarnings("serial")
public class EnumBody extends TypeBody {
    
    private List<EnumConstantDeclaration> constants;

    public EnumBody() {
    }
    
    public EnumBody(List<EnumConstantDeclaration> constants, List<BodyDeclaration> members) {
        super(members);
        this.constants = constants;
    }
    
    public List<EnumConstantDeclaration> getConstants() {
        return constants;
    }
    
    public void setConstants(List<EnumConstantDeclaration> constants) {
        this.constants = constants;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((constants == null) ? 0 : constants.hashCode());
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
        EnumBody other = (EnumBody) obj;
        if (constants == null) {
            if (other.constants != null) {
                return false;
            }
        } else if (!constants.equals(other.constants)) {
            return false;
        }
        return true;
    }
}
