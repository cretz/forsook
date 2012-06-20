package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.InnerBlockStatement;
import org.forsook.parser.java.ast.statement.LocalClassDeclarationStatement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.2")
@ParseletDefinition(
        name = "forsook.java.innerBlockStatement",
        emits = InnerBlockStatement.class,
        needs = {
            LocalClassDeclarationStatement.class,
            LocalVariableDeclarationStatement.class,
            Statement.class
        }
)
public class InnerBlockStatementParselet extends JavaParselet<InnerBlockStatement> {
    
    @Override
    public InnerBlockStatement parse(Parser parser) {
        return (InnerBlockStatement) parser.first(
                LocalClassDeclarationStatement.class,
                LocalVariableDeclarationStatement.class,
                Statement.class); 
    }

}
