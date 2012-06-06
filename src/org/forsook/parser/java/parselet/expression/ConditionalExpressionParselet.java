package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ConditionalExpression;
import org.forsook.parser.java.ast.expression.ConditionalOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.25")
@ParseletDefinition(
        name = "forsook.java.conditionalExpression",
        emits = ConditionalExpression.class,
        needs = { ConditionalOrExpression.class, ConditionalOperatorExpression.class }
)
public class ConditionalExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(ConditionalOrExpression.class, 
                ConditionalOperatorExpression.class); 
    }
}
