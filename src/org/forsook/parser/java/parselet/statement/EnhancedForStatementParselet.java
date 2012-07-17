package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.EnhancedForStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;

@JlsReference("14.14.2")
@ParseletDefinition(
        name = "forsook.java.enhancedForStatement",
        emits = EnhancedForStatement.class,
        needs = {
            LocalVariableDeclarationExpression.class,
            Expression.class,
            Statement.class
        }
)
public class EnhancedForStatementParselet extends ForStatementParselet<EnhancedForStatement> {

    @Override
    public EnhancedForStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip("for")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ':')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //var
        LocalVariableDeclarationExpression var = 
                parser.next(LocalVariableDeclarationExpression.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //colon
        if (!parser.peekPresentAndSkip(':')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //iterable
        Expression iterable = parser.next(Expression.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //body
        Statement body = parser.next(Statement.class);
        if (body == null) {
            return null;
        }
        return new EnhancedForStatement(var, iterable, body);
    }
}
