package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.NegatedExpression;
import org.forsook.parser.java.ast.expression.PostfixExpression;
import org.forsook.parser.java.ast.expression.UnaryNotPlusMinusExpression;

@JlsReference("15.15")
@ParseletDefinition(
        name = "forsook.java.unaryNotPlusMinusExpression",
        emits = UnaryNotPlusMinusExpression.class,
        needs = { 
            PostfixExpression.class,
            NegatedExpression.class,
            CastExpression.class
        }
)
public class UnaryNotPlusMinusExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(
                CastExpression.class,
                PostfixExpression.class,
                NegatedExpression.class);
    }
}
