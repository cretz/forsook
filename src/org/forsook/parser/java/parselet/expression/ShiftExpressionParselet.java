package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ShiftExpression;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.AdditiveExpression;

@JlsReference("15.19")
@ParseletDefinition(
        name = "forsook.java.shiftExpression",
        emits = ShiftExpression.class,
        needs = { AdditiveExpression.class, ShiftOperatorExpression.class }
)
public class ShiftExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(AdditiveExpression.class, 
                ShiftOperatorExpression.class); 
    }
}
