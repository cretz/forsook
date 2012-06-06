package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PrefixIncrementExpression;
import org.forsook.parser.java.ast.expression.SignedExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;
import org.forsook.parser.java.ast.expression.UnaryNotPlusMinusExpression;

@JlsReference("15.15")
@ParseletDefinition(
        name = "forsook.java.unaryExpression",
        emits = UnaryExpression.class,
        needs = { 
            PrefixIncrementExpression.class,
            SignedExpression.class,
            UnaryNotPlusMinusExpression.class
        }
)
public class UnaryExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(
                PrefixIncrementExpression.class,
                SignedExpression.class,
                UnaryNotPlusMinusExpression.class);
    }
}
