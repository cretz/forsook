package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.ParenthesizedExpression;

@JlsReference("15.8.5")
@ParseletDefinition(
        name = "forsook.java.parenthesizedExpression",
        emits = ParenthesizedExpression.class,
        needs = Expression.class
)
public class ParenthesizedExpressionParselet extends ExpressionParselet<ParenthesizedExpression> {

    @Override
    public ParenthesizedExpression parse(Parser parser) {
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = parser.next(Expression.class);
        if (expression == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        return new ParenthesizedExpression(expression);
    }
}
