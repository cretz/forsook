package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.IfNoShortIfStatement;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("14.9")
@ParseletDefinition(
        name = "forsook.java.ifNoShortIfStatement",
        emits = IfNoShortIfStatement.class,
        needs = { 
            Expression.class, 
            NoShortIfStatement.class
        }
)
public class IfNoShortIfStatementParselet extends StatementParselet<IfNoShortIfStatement> {

    @Override
    public IfNoShortIfStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip("if")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushLookAhead(')')) {
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
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //then statement
        Statement thenStatement = (Statement) parser.next(NoShortIfStatement.class);
        if (thenStatement == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //else?
        if (!parser.peekPresentAndSkip("else")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //statement (must be no-short-if)
        Statement elseStatement = (Statement) parser.next(NoShortIfStatement.class);
        if (elseStatement == null) {
            return null;
        }
        return new IfNoShortIfStatement(condition, thenStatement, elseStatement);
    }
}
