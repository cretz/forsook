package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.RelationalExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression.RelationalOperator;
import org.forsook.parser.java.ast.expression.ShiftExpression;
import org.forsook.parser.java.ast.expression.TypeExpression;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("15.20")
@ParseletDefinition(
        name = "forsook.java.relationalOperatorExpression",
        emits = RelationalOperatorExpression.class,
        needs = { ShiftExpression.class, RelationalExpression.class },
        recursiveMinimumSize = 2
)
public class RelationalOperatorExpressionParselet 
        extends ExpressionParselet<RelationalOperatorExpression> {

    @Override
    public RelationalOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead("<=", ">=", "<", ">", "instanceof")) {
            return null;
        }
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
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = null;
        if (operator == RelationalOperator.INSTANCE_OF) {
            Type type = parser.next(ReferenceType.class);
            if (type != null) {
                right = new TypeExpression(type);
            }
        } else {
            right = (Expression) parser.next(ShiftExpression.class);
        }
        if (right == null) {
            return null;
        }
        return new RelationalOperatorExpression(left, operator, right);
    }
}
