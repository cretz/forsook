package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.ast.statement.Statement;

@SuppressWarnings("serial")
public class TypeDeclarationStatement extends Statement {

    private TypeDeclaration<?> typeDeclaration;
    
    public TypeDeclarationStatement() {
    }
    
    public TypeDeclarationStatement(TypeDeclaration<?> typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }
    
    public TypeDeclaration<?> getTypeDeclaration() {
        return typeDeclaration;
    }
    
    public void setTypeDeclaration(TypeDeclaration<?> typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((typeDeclaration == null) ? 0 : typeDeclaration.hashCode());
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
        TypeDeclarationStatement other = (TypeDeclarationStatement) obj;
        if (typeDeclaration == null) {
            if (other.typeDeclaration != null) {
                return false;
            }
        } else if (!typeDeclaration.equals(other.typeDeclaration)) {
            return false;
        }
        return true;
    }
}
