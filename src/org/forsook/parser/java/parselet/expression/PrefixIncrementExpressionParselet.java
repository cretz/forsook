package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PrefixIncrementExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;

@JlsReference("15.15")
@ParseletDefinition(
        name = "forsook.java.prefixIncrementExpression",
        emits = PrefixIncrementExpression.class,
        needs = UnaryExpression.class
)
public class PrefixIncrementExpressionParselet extends ExpressionParselet<PrefixIncrementExpression> {

    @Override
    public PrefixIncrementExpression parse(Parser parser) {
        //increment
        boolean increment = parser.peekPresentAndSkip("++");
        if (!increment && !parser.peekPresentAndSkip("--")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = (Expression) parser.next(UnaryExpression.class);
        if (expression == null) {
            return null;
        }
        return new PrefixIncrementExpression(increment, expression);
    }
}
