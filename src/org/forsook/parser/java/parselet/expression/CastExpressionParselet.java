package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.UnaryExpression;
import org.forsook.parser.java.ast.type.ReferenceType;

@JlsReference("15.16")
@ParseletDefinition(
        name = "forsook.java.castExpression",
        emits = CastExpression.class,
        needs = {
            ReferenceType.class,
            UnaryExpression.class,
            CastExpression.class
        }
)
public class CastExpressionParselet extends ExpressionParselet<CastExpression> {

    @Override
    public CastExpression parse(Parser parser) {
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type
        ReferenceType type = parser.next(ReferenceType.class);
        if (type == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = (Expression) parser.first(
                UnaryExpression.class, CastExpression.class);
        if (expression == null) {
            expression = parsePrimaryExpression(parser, true);
            if (expression == null) {
                return null;
            }
        }
        return new CastExpression(type, expression);
    }
}
