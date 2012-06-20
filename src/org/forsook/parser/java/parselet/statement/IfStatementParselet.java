package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.IfStatement;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("14.9")
@ParseletDefinition(
        name = "forsook.java.ifStatement",
        emits = IfStatement.class,
        needs = { 
            Expression.class, 
            Statement.class,
            NoShortIfStatement.class
        }
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
        Statement thenStatement = parser.next(Statement.class);
        if (thenStatement == null) {
            //try no short if
            thenStatement = (Statement) parser.next(NoShortIfStatement.class);
            if (thenStatement == null) {
                return null;
            }
        }
        return new IfStatement(condition, thenStatement, null);
    }
}
