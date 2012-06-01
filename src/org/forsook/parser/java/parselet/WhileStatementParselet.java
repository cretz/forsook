package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.Statement;
import org.forsook.parser.java.ast.WhileStatement;

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
        //body
        Statement body = parser.next(Statement.class);
        if (body == null) {
            return null;
        }
        return new WhileStatement(condition, body);
    }
}
