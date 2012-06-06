package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ExclusiveOrExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.InclusiveOrExpression;
import org.forsook.parser.java.ast.expression.InclusiveOrOperatorExpression;

@JlsReference("15.22")
@ParseletDefinition(
        name = "forsook.java.inclusiveOrExpression",
        emits = InclusiveOrExpression.class,
        needs = { ExclusiveOrExpression.class, InclusiveOrOperatorExpression.class }
)
public class InclusiveOrExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(ExclusiveOrExpression.class, 
                InclusiveOrOperatorExpression.class); 
    }
}
