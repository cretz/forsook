package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalExpression;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("15.26")
@ParseletDefinition(
        name = "forsook.java.assignmentExpression",
        emits = AssignmentExpression.class,
        needs = { ConditionalExpression.class, AssignmentOperatorExpression.class }
)
public class AssignmentExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(ConditionalExpression.class,
                AssignmentOperatorExpression.class);
    }

}
