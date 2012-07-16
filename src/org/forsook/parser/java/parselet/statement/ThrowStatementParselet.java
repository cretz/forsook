package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.ThrowStatement;

@JlsReference("14.18")
@ParseletDefinition(
        name = "forsook.java.throwStatement",
        emits = ThrowStatement.class,
        needs = Expression.class
)
public class ThrowStatementParselet extends StatementParselet<ThrowStatement> {

    @Override
    public ThrowStatement parse(Parser parser) {
        //throw
        if (!parser.peekPresentAndSkip("throw")) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ';')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression?
        Expression expression = parser.next(Expression.class);
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
        return new ThrowStatement(expression);
    }
}
