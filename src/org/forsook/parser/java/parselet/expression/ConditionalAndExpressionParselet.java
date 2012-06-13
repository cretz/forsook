package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalAndExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.InclusiveOrExpression;

@JlsReference("15.23")
@ParseletDefinition(
        name = "forsook.java.conditionalAndExpression",
        emits = ConditionalAndExpression.class,
        needs = { InclusiveOrExpression.class, ConditionalAndOperatorExpression.class }
)
public class ConditionalAndExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(ConditionalAndOperatorExpression.class,
                InclusiveOrExpression.class);
    }
}
