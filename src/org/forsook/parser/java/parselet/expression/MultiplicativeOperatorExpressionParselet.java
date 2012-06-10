package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.MultiplicativeExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression.MultiplicativeOperator;

@JlsReference("15.17")
@ParseletDefinition(
        name = "forsook.java.multiplicativeOperatorExpression",
        emits = MultiplicativeOperatorExpression.class,
        needs = { UnaryExpression.class, MultiplicativeExpression.class },
        recursiveMinimumSize = 2
)
public class MultiplicativeOperatorExpressionParselet 
        extends ExpressionParselet<MultiplicativeOperatorExpression> {

    @Override
    public MultiplicativeOperatorExpression parse(Parser parser) {
        //left
        Expression left = (Expression) parser.next(MultiplicativeExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        MultiplicativeOperator operator;
        if (parser.peekPresentAndSkip('*')) {
            operator = MultiplicativeOperator.MULTIPLY;
        } else if (parser.peekPresentAndSkip('/')) {
            operator = MultiplicativeOperator.DIVIDE;
        } else if (parser.peekPresentAndSkip('%')) {
            operator = MultiplicativeOperator.REMAINDER;
        } else {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(UnaryExpression.class);
        if (right == null) {
            return null;
        }
        return new MultiplicativeOperatorExpression(left, operator, right);
    }
}
