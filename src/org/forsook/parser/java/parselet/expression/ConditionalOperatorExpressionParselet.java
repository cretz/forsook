package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalExpression;
import org.forsook.parser.java.ast.expression.ConditionalOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.25")
@ParseletDefinition(
        name = "forsook.java.conditionalOperator",
        emits = ConditionalOperatorExpression.class,
        needs = {
            ConditionalOrExpression.class,
            Expression.class,
            ConditionalExpression.class
        }
)
public class ConditionalOperatorExpressionParselet 
        extends ExpressionParselet<ConditionalOperatorExpression> {

    @Override
    public ConditionalOperatorExpression parse(Parser parser) {
        //condition
        Expression condition = (Expression) parser.next(ConditionalOrExpression.class);
        if (condition == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //question mark
        if (!parser.peekPresentAndSkip('?')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //then
        Expression thenExpression = parser.next(Expression.class);
        if (thenExpression == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //colon
        if (!parser.peekPresentAndSkip(':')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //else
        Expression elseExpression = (Expression) parser.next(ConditionalExpression.class);
        if (elseExpression == null) {
            return null;
        }
        return new ConditionalOperatorExpression(condition, thenExpression, elseExpression);
    }
}
