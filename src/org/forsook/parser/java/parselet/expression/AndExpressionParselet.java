package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AndExpression;
import org.forsook.parser.java.ast.expression.AndOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.EqualityExpression;

@JlsReference("15.22")
@ParseletDefinition(
        name = "forsook.java.andExpression",
        emits = AndExpression.class,
        needs = { EqualityExpression.class, AndOperatorExpression.class }
)
public class AndExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(EqualityExpression.class, 
                AndOperatorExpression.class); 
    }
}
