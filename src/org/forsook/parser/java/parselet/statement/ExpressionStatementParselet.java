package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.ExpressionStatement;

@JlsReference("14.8")
@ParseletDefinition(
        name = "forsook.java.expressionStatement",
        emits = ExpressionStatement.class,
        needs = {
            AssignmentExpression.class,
            PrefixIncrementExpression.class,
            PostfixIncrementExpression.class,
            MethodInvocationExpression.class,
            ClassInstanceCreationExpression.class
        }
)
public class ExpressionStatementParselet extends StatementParselet<ExpressionStatement> {

    @Override
    public ExpressionStatement parse(Parser parser) {
        Expression expr = (Expression) parser.first(
                AssignmentExpression.class,
                PrefixIncrementExpression.class,
                PostfixIncrementExpression.class,
                MethodInvocationExpression.class,
                ClassInstanceCreationExpression.class
            );
        if (expr == null) {
            return null;
        }
        return new ExpressionStatement(expr);
    }
}
