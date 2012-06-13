package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalAndExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.24")
@ParseletDefinition(
        name = "forsook.java.conditionalOrExpression",
        emits = ConditionalOrExpression.class,
        needs = { ConditionalAndExpression.class, ConditionalOrOperatorExpression.class }
)
public class ConditionalOrExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(ConditionalOrOperatorExpression.class,
                ConditionalAndExpression.class); 
    }
}
