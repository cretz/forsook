package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.DoStatement;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("14.13")
@ParseletDefinition(
        name = "forsook.java.doStatement",
        emits = DoStatement.class,
        needs = { Statement.class, Expression.class }
)
public class DoStatementParselet extends StatementParselet<DoStatement> {

    @Override
    public DoStatement parse(Parser parser) {
        //do
        if (!parser.peekPresentAndSkip("do")) {
            return null;
        }
        //lookahead
        if (!parser.pushLookAhead("while")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //statement
        Statement statement = parser.next(Statement.class);
        if (statement == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //while
        if (!parser.peekPresentAndSkip("while")) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
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
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new DoStatement(statement, condition);
    }
}
