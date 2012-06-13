package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.MultiplicativeExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;

@JlsReference("15.17")
@ParseletDefinition(
        name = "forsook.java.multiplicativeExpression",
        emits = MultiplicativeExpression.class,
        needs = { UnaryExpression.class, MultiplicativeExpression.class }
)
public class MultiplicativeExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(MultiplicativeOperatorExpression.class,
                UnaryExpression.class);
    }
}
