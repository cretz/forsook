package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalAndExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrOperatorExpression;
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
        //lookahead
        if (!parser.pushLastDepthLookAhead(parser.getAstDepth(), "||")) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(ConditionalOrExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        if (!parser.peekPresentAndSkip("||")) {
            if (left instanceof ConditionalOrOperatorExpression) {
                parser.popLookAhead();
                return (ConditionalOrOperatorExpression) left;
            }
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(ConditionalAndExpression.class, 
                ConditionalOrOperatorExpression.class);
        if (right == null) {
            return null;
        }
        return new ConditionalOrOperatorExpression(left, right);
    }
}
