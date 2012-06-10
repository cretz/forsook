package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalOrExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.24")
@ParseletDefinition(
        name = "forsook.java.conditionalOrOperatorExpression",
        emits = ConditionalOrOperatorExpression.class,
        needs = { ConditionalOrExpression.class, ConditionalAndExpression.class },
        recursiveMinimumSize = 3
)
public class ConditionalOrOperatorExpressionParselet 
        extends ExpressionParselet<ConditionalOrOperatorExpression> {

    @Override
    public ConditionalOrOperatorExpression parse(Parser parser) {
        //left
        Expression left = (Expression) parser.next(ConditionalOrExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        if (!parser.peekPresentAndSkip("||")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(ConditionalAndExpression.class);
        if (right == null) {
            return null;
        }
        return new ConditionalOrOperatorExpression(left, right);
    }
}
