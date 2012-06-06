package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AdditiveExpression;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.MultiplicativeExpression;

@JlsReference("15.18")
@ParseletDefinition(
        name = "forsook.java.additiveExpression",
        emits = AdditiveExpression.class,
        needs = { MultiplicativeExpression.class, AdditiveOperatorExpression.class }
)
public class AdditiveExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(MultiplicativeExpression.class, 
                AdditiveOperatorExpression.class); 
    }
}
