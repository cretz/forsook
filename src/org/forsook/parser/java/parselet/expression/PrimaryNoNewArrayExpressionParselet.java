package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PrimaryNoNewArrayExpression;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("15.8")
@ParseletDefinition(
        name = "forsook.java.primaryNoNewArrayExpression",
        emits = PrimaryNoNewArrayExpression.class,
        needs = {
            LiteralExpression.class,
            ClassExpression.class,
            ThisExpression.class,
            ParenthesizedExpression.class,
            ClassInstanceCreationExpression.class,
            FieldAccessExpression.class,
            MethodInvocationExpression.class,
            ArrayAccessExpression.class
        }
)
public class PrimaryNoNewArrayExpressionParselet extends JavaParselet<PrimaryNoNewArrayExpression> {

    @Override
    public PrimaryNoNewArrayExpression parse(Parser parser) {
        return (PrimaryNoNewArrayExpression) parser.first(
                LiteralExpression.class,
                ClassExpression.class,
                ThisExpression.class,
                ParenthesizedExpression.class,
                ClassInstanceCreationExpression.class,
                FieldAccessExpression.class,
                MethodInvocationExpression.class,
                ArrayAccessExpression.class
            );
    }

}
