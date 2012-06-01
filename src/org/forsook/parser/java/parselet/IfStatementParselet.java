package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.IfStatement;
import org.forsook.parser.java.ast.Statement;

@JlsReference("14.9")
@ParseletDefinition(
        name = "forsook.java.ifStatement",
        emits = IfStatement.class,
        needs = { Expression.class, Statement.class }
)
public class IfStatementParselet extends StatementParselet<IfStatement> {

    @Override
    public IfStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip("if")) {
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
        //condition
        Expression condition = parser.next(Expression.class);
        if (condition == null) {
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
        //then statement
        Statement thenStatement = parser.next(Statement.class);
        if (thenStatement == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //else?
        Statement elseStatement = null;
        if (isStatementNoShortIf(thenStatement) && 
                parser.peekPresentAndSkip("else")) {
            elseStatement = parser.next(Statement.class);
            if (elseStatement == null) {
                return null;
            }
        }
        return new IfStatement(condition, thenStatement, elseStatement);
    }
}
