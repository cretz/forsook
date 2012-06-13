package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AdditiveExpression;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression.AdditiveOperator;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.MultiplicativeExpression;

@JlsReference("15.18")
@ParseletDefinition(
        name = "forsook.java.additiveOperatorExpression",
        emits = AdditiveOperatorExpression.class,
        needs = { MultiplicativeExpression.class, AdditiveExpression.class },
        recursiveMinimumSize = 2
)
public class AdditiveOperatorExpressionParselet 
        extends ExpressionParselet<AdditiveOperatorExpression> {

    @Override
    public AdditiveOperatorExpression parse(Parser parser) {
        //lookahead
        if (!parser.lookAhead('+', '-')) {
            return null;
        }
        //left
        Expression left = (Expression) parser.next(AdditiveExpression.class);
        if (left == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        AdditiveOperator operator;
        if (parser.peekPresentAndSkip('+')) {
            operator = AdditiveOperator.PLUS;
        } else if (parser.peekPresentAndSkip('-')) {
            operator = AdditiveOperator.MINUS;
        } else {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //right
        Expression right = (Expression) parser.next(MultiplicativeExpression.class);
        if (right == null) {
            return null;
        }
        return new AdditiveOperatorExpression(left, operator, right);
    }
}
