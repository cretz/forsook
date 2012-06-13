package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.RelationalExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression.EqualityOperator;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.EqualityExpression;

@JlsReference("15.21")
@ParseletDefinition(
        name = "forsook.java.equalityOperatorExpression",
        emits = EqualityOperatorExpression.class,
        needs = { EqualityExpression.class, RelationalExpression.class },
        recursiveMinimumSize = 3
)
public class EqualityOperatorExpressionParselet 
        extends ExpressionParselet<EqualityOperatorExpression> {

    @Override
    public EqualityOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.lookAhead("==", "!=")) {
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
        EqualityOperator operator;
        if (parser.peekPresentAndSkip("==")) {
            operator = EqualityOperator.EQUAL;
        } else if (parser.peekPresentAndSkip("!=")) {
            operator = EqualityOperator.NOT_EQUAL;
        } else {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(EqualityExpression.class);
        if (right == null) {
            return null;
        }
        return new EqualityOperatorExpression(left, operator, right);
    }
}
