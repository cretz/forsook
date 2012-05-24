package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class TypeDeclarationStatement extends Statement {

    private TypeDeclaration typeDeclaration;
    
    public TypeDeclarationStatement() {
    }
    
    public TypeDeclarationStatement(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }
    
    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }
    
    public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }
}
