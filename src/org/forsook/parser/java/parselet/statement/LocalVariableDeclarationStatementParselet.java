package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationStatement;

@JlsReference("14.4")
@ParseletDefinition(
        name = "forsook.java.localVariableDeclarationStatement",
        emits = LocalVariableDeclarationStatement.class,
        needs = LocalVariableDeclarationExpression.class
)
public class LocalVariableDeclarationStatementParselet extends StatementParselet<LocalVariableDeclarationStatement> {
    
    @Override
    public LocalVariableDeclarationStatement parse(Parser parser) {
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ';')) {
            return null;
        }
        //declaration
        LocalVariableDeclarationExpression expression = parser.next(
                LocalVariableDeclarationExpression.class);
        if (expression == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new LocalVariableDeclarationStatement(expression);
    }

}
