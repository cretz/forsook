package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalAndExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.InclusiveOrExpression;

@JlsReference("15.23")
@ParseletDefinition(
        name = "forsook.java.conditionalAndOperatorExpression",
        emits = ConditionalAndOperatorExpression.class,
        needs = { ConditionalAndExpression.class, InclusiveOrExpression.class },
        recursiveMinimumSize = 2
)
public class ConditionalAndOperatorExpressionParselet 
        extends ExpressionParselet<ConditionalAndOperatorExpression> {

    @Override
    public ConditionalAndOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLastDepthLookAhead(parser.getAstDepth(), "&&")) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(ConditionalAndExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        if (!parser.peekPresentAndSkip("&&")) {
            if (left instanceof ConditionalAndOperatorExpression) {
                parser.popLookAhead();
                return (ConditionalAndOperatorExpression) left;
            }
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(InclusiveOrExpression.class,
                ConditionalAndOperatorExpression.class);
        if (right == null) {
            return null;
        }
        return new ConditionalAndOperatorExpression(left, right);
    }
}
