package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PostfixExpression;
import org.forsook.parser.java.ast.expression.PostfixIncrementExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.14")
@ParseletDefinition(
        name = "forsook.java.postfixExpression",
        emits = PostfixExpression.class,
        needs = {
            PrimaryExpression.class,
            QualifiedName.class,
            PostfixIncrementExpression.class
        }
)
public class PostfixExpressionParselet extends ExpressionParselet<Expression> {

    @Override
    public Expression parse(Parser parser) {
        return (Expression) parser.first(PrimaryExpression.class,
                PostfixIncrementExpression.class,
                QualifiedName.class);
    }
}
