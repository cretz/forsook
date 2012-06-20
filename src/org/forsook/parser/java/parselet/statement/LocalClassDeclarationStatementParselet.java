package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.packag.TypeDeclaration;
import org.forsook.parser.java.ast.statement.LocalClassDeclarationStatement;

@JlsReference("14.3")
@ParseletDefinition(
        name = "forsook.java.localClassDeclarationStatement",
        emits = LocalClassDeclarationStatement.class,
        needs = {
            ClassOrInterfaceDeclaration.class,
            EnumDeclaration.class
        }
)
public class LocalClassDeclarationStatementParselet extends StatementParselet<LocalClassDeclarationStatement> {
    
    @Override
    public LocalClassDeclarationStatement parse(Parser parser) {
        TypeDeclaration<?> declaration = (TypeDeclaration<?>) parser.first(
                ClassOrInterfaceDeclaration.class,
                EnumDeclaration.class);
        if (declaration == null) {
            return null;
        }
        return new LocalClassDeclarationStatement(declaration);
    }

}
