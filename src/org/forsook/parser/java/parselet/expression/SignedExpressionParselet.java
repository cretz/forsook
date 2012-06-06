package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.SignedExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;

@JlsReference("15.15")
@ParseletDefinition(
        name = "forsook.java.signedExpression",
        emits = SignedExpression.class,
        needs = UnaryExpression.class
)
public class SignedExpressionParselet extends ExpressionParselet<SignedExpression> {

    @Override
    public SignedExpression parse(Parser parser) {
        //positive
        boolean positive = parser.peekPresentAndSkip('+');
        if (!positive && !parser.peekPresentAndSkip('-')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = (Expression) parser.next(UnaryExpression.class);
        if (expression == null) {
            return null;
        }
        return new SignedExpression(positive, expression);
    }
}
