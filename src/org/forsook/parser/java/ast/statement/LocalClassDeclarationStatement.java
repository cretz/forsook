package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.packag.TypeDeclaration;

@JlsReference("14.3")
@SuppressWarnings("serial")
public class LocalClassDeclarationStatement extends Statement implements InnerBlockStatement {

    private TypeDeclaration<?> declaration;
    
    public LocalClassDeclarationStatement() {
    }

    public LocalClassDeclarationStatement(TypeDeclaration<?> declaration) {
        this.declaration = declaration;
    }
    
    public TypeDeclaration<?> getDeclaration() {
        return declaration;
    }
    
    public void setDeclaration(TypeDeclaration<?> declaration) {
        this.declaration = declaration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((declaration == null) ? 0 : declaration.hashCode());
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
        LocalClassDeclarationStatement other = (LocalClassDeclarationStatement) obj;
        if (declaration == null) {
            if (other.declaration != null) {
                return false;
            }
        } else if (!declaration.equals(other.declaration)) {
            return false;
        }
        return true;
    }
}
