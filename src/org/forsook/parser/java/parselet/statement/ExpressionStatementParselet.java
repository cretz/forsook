package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.StatementExpression;

@JlsReference("14.8")
@ParseletDefinition(
        name = "forsook.java.expressionStatement",
        emits = ExpressionStatement.class,
        needs = StatementExpression.class
)
public class ExpressionStatementParselet extends StatementParselet<ExpressionStatement> {

    @Override
    public ExpressionStatement parse(Parser parser) {
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ';')) {
            return null;
        }
        //expression
        Expression expr = (Expression) parser.next(StatementExpression.class);
        if (expr == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //semi-colon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new ExpressionStatement(expr);
    }
}
