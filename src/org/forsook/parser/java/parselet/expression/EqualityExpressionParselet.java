package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.EqualityExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.RelationalExpression;

@JlsReference("15.21")
@ParseletDefinition(
        name = "forsook.java.equalityExpression",
        emits = EqualityExpression.class,
        needs = { RelationalExpression.class, EqualityOperatorExpression.class }
)
public class EqualityExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(RelationalExpression.class, 
                EqualityOperatorExpression.class); 
    }
}
