package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.UnaryExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression.UnaryOperator;

@JlsReference("15.15")
@ParseletDefinition(
        name = "forsook.java.unaryExpression",
        emits = UnaryExpression.class,
        needs = { UnaryExpression.class, CastExpression.class }
)
public class UnaryExpressionParselet extends ExpressionParselet<UnaryExpression> {

    @Override
    public UnaryExpression parse(Parser parser) {
        //operator
        UnaryOperator operator = null;
        if (parser.peekPresentAndSkip("++")) {
            operator = UnaryOperator.PRE_INCREMENT;
        } else if (parser.peekPresentAndSkip("--")) {
            operator = UnaryOperator.PRE_DECREMENT;
        } else if (parser.peekPresentAndSkip('+')) {
            operator = UnaryOperator.POSITIVE;
        } else if (parser.peekPresentAndSkip('-')) {
            operator = UnaryOperator.NEGATIVE;
        } else if (parser.peekPresentAndSkip('~')) {
            operator = UnaryOperator.INVERSE;
        } else if (parser.peekPresentAndSkip('!')) {
            operator = UnaryOperator.NOT;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = (Expression) parser.first(
                UnaryExpression.class, CastExpression.class);
        if (operator == null && expression instanceof UnaryExpression &&
                ((UnaryExpression) expression).getOperator().isPostfix()) {
            return (UnaryExpression) expression;
        } else if (expression == null) {
            expression = parsePrimaryExpression(parser);
            if (expression == null) {
                return null;
            }
        }
        if (operator == null) {
            if (parser.peekPresentAndSkip("++")) {
                operator = UnaryOperator.POST_INCREMENT;
            } else if (parser.peekPresentAndSkip("--")) {
                operator = UnaryOperator.POST_DECREMENT;
            } else {
                return null;
            }
        }
        return new UnaryExpression(expression, operator);
    }
}
