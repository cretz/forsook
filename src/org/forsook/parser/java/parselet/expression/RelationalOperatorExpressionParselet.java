package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.RelationalExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression.RelationalOperator;
import org.forsook.parser.java.ast.expression.ShiftExpression;

@JlsReference("15.20")
@ParseletDefinition(
        name = "forsook.java.relationalOperatorExpression",
        emits = RelationalOperatorExpression.class,
        needs = { ShiftExpression.class, RelationalExpression.class }
)
public class RelationalOperatorExpressionParselet 
        extends ExpressionParselet<RelationalOperatorExpression> {

    @Override
    public RelationalOperatorExpression parse(Parser parser) {
        //left
        Expression left = (Expression) parser.next(RelationalExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        RelationalOperator operator;
        if (parser.peekPresentAndSkip("<=")) {
            operator = RelationalOperator.LESS_THAN_OR_EQUAL;
        } else if (parser.peekPresentAndSkip(">=")) {
            operator = RelationalOperator.GREATER_THAN_OR_EQUAL;
        } else if (parser.peekPresentAndSkip('<')) {
            operator = RelationalOperator.LESS_THAN;
        } else if (parser.peekPresentAndSkip('>')) {
            operator = RelationalOperator.GREATER_THAN;
        } else if (parser.peekPresentAndSkip("instanceof")) {
            operator = RelationalOperator.INSTANCE_OF;
        } else {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(ShiftExpression.class);
        if (right == null) {
            return null;
        }
        return new RelationalOperatorExpression(left, operator, right);
    }
}
