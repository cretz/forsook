package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression;
import org.forsook.parser.java.ast.expression.ClassInstanceCreationExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.PostfixIncrementExpression;
import org.forsook.parser.java.ast.expression.PrefixIncrementExpression;
import org.forsook.parser.java.ast.statement.StatementExpression;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.8")
@ParseletDefinition(
        name = "forsook.java.statementExpression",
        emits = StatementExpression.class,
        needs = {
            AssignmentOperatorExpression.class,
            PrefixIncrementExpression.class,
            PostfixIncrementExpression.class,
            MethodInvocationExpression.class,
            ClassInstanceCreationExpression.class
        }
)
public class StatementExpressionParselet extends JavaParselet<StatementExpression> {

    @Override
    public StatementExpression parse(Parser parser) {
        return (StatementExpression) parser.first(
                AssignmentOperatorExpression.class,
                PrefixIncrementExpression.class,
                PostfixIncrementExpression.class,
                MethodInvocationExpression.class,
                ClassInstanceCreationExpression.class
            );
    }
}
