package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.NegatedExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;

@JlsReference("15.15")
@ParseletDefinition(
        name = "forsook.java.negatedExpression",
        emits = NegatedExpression.class,
        needs = UnaryExpression.class
)
public class NegatedExpressionParselet extends ExpressionParselet<NegatedExpression> {

    @Override
    public NegatedExpression parse(Parser parser) {
        //not
        boolean not = parser.peekPresentAndSkip('!');
        if (!not && !parser.peekPresentAndSkip('~')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = (Expression) parser.next(UnaryExpression.class);
        if (expression == null) {
            return null;
        }
        return new NegatedExpression(not, expression);
    }
}
