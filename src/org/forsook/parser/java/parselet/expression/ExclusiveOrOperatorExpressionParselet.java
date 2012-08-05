package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AndExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.22")
@ParseletDefinition(
        name = "forsook.java.exclusiveOrOperatorExpression",
        emits = ExclusiveOrOperatorExpression.class,
        needs = { ExclusiveOrExpression.class, AndExpression.class },
        recursiveMinimumSize = 2
)
public class ExclusiveOrOperatorExpressionParselet 
        extends ExpressionParselet<ExclusiveOrOperatorExpression> {

    @Override
    public ExclusiveOrOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead('^')) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(ExclusiveOrExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        if (!parser.peekPresentAndSkip('^')) {
            if (left instanceof ExclusiveOrOperatorExpression) {
                parser.popLookAhead();
                return (ExclusiveOrOperatorExpression) left;
            }
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(AndExpression.class, 
                ExclusiveOrOperatorExpression.class);
        if (right == null) {
            return null;
        }
        return new ExclusiveOrOperatorExpression(left, right);
    }
}
