package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.WhileStatement;

@JlsReference("14.12")
@ParseletDefinition(
        name = "forsook.java.whileStatement",
        emits = WhileStatement.class,
        needs = { Expression.class, Statement.class }
)
public class WhileStatementParselet extends StatementParselet<WhileStatement> {

    @Override
    public WhileStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip("while")) {
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
        //body
        Statement body = parseStatement(parser);
        if (body == null) {
            return null;
        }
        return new WhileStatement(condition, body);
    }
    
    protected Statement parseStatement(Parser parser) {
        //abstracted for the no-short-if
        return parser.next(Statement.class);
    }
}
