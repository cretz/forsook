package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AndExpression;
import org.forsook.parser.java.ast.expression.AndOperatorExpression;
import org.forsook.parser.java.ast.expression.EqualityExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.22")
@ParseletDefinition(
        name = "forsook.java.andOperatorExpression",
        emits = AndOperatorExpression.class,
        needs = { AndExpression.class, EqualityExpression.class },
        recursiveMinimumSize = 2
)
public class AndOperatorExpressionParselet extends ExpressionParselet<AndOperatorExpression> {

    @Override
    public AndOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead('&')) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(AndExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        if (!parser.peekPresentAndSkip('&')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(EqualityExpression.class);
        if (right == null) {
            return null;
        }
        return new AndOperatorExpression(left, right);
    }
}
