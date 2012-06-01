package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.EnhancedForStatement;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.Statement;
import org.forsook.parser.java.ast.VariableDeclarationExpression;

@JlsReference("14.14.2")
@ParseletDefinition(
        name = "forsook.java.enhancedForStatement",
        emits = EnhancedForStatement.class,
        needs = {
            VariableDeclarationExpression.class,
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
        //spacing
        parseWhiteSpaceAndComments(parser);
        //var
        VariableDeclarationExpression var = parser.next(VariableDeclarationExpression.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //colon
        if (!parser.peekPresentAndSkip(':')) {
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
