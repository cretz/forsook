package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.InclusiveOrExpression;
import org.forsook.parser.java.ast.expression.InclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.22")
@ParseletDefinition(
        name = "forsook.java.inclusiveOrOperatorExpression",
        emits = InclusiveOrOperatorExpression.class,
        needs = { InclusiveOrExpression.class, ExclusiveOrExpression.class },
        recursiveMinimumSize = 2
)
public class InclusiveOrOperatorExpressionParselet 
        extends ExpressionParselet<InclusiveOrOperatorExpression> {

    @Override
    public InclusiveOrOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead('|')) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(InclusiveOrExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        if (!parser.peekPresentAndSkip('|')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(ExclusiveOrExpression.class, 
                InclusiveOrOperatorExpression.class);
        if (right == null) {
            return null;
        }
        return new InclusiveOrOperatorExpression(left, right);
    }
}
