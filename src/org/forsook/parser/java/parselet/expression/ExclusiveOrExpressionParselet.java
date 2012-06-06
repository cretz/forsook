package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AndExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.22")
@ParseletDefinition(
        name = "forsook.java.exclusiveOrExpression",
        emits = ExclusiveOrExpression.class,
        needs = { AndExpression.class, ExclusiveOrOperatorExpression.class }
)
public class ExclusiveOrExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(AndExpression.class, 
                ExclusiveOrOperatorExpression.class); 
    }
}
