package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.RelationalExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression;
import org.forsook.parser.java.ast.expression.ShiftExpression;

@JlsReference("15.20")
@ParseletDefinition(
        name = "forsook.java.relationalExpression",
        emits = RelationalExpression.class,
        needs = { ShiftExpression.class, RelationalOperatorExpression.class }
)
public class RelationalExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(RelationalOperatorExpression.class,
                ShiftExpression.class);
    }
}
