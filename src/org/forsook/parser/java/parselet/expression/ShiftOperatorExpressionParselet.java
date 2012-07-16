package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AdditiveExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.ShiftExpression;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression.ShiftOperator;

@JlsReference("15.19")
@ParseletDefinition(
        name = "forsook.java.shiftOperatorExpression",
        emits = ShiftOperatorExpression.class,
        needs = { AdditiveExpression.class, ShiftExpression.class },
        recursiveMinimumSize = 3
)
public class ShiftOperatorExpressionParselet 
        extends ExpressionParselet<ShiftOperatorExpression> {

    @Override
    public ShiftOperatorExpression parse(Parser parser) {
        if (!parser.pushLookAhead("<<", ">>")) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(ShiftExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        ShiftOperator operator;
        if (parser.peekPresentAndSkip("<<")) {
            operator = ShiftOperator.LEFT;
        } else if (parser.peekPresentAndSkip(">>")) {
            operator = ShiftOperator.SIGNED_RIGHT;
        } else if (parser.peekPresentAndSkip(">>>")) {
            operator = ShiftOperator.UNSIGNED_RIGHT;
        } else {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(AdditiveExpression.class, 
                ShiftOperatorExpression.class);
        if (right == null) {
            return null;
        }
        return new ShiftOperatorExpression(left, operator, right);
    }
}
