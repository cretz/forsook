package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayCreationExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.expression.PrimaryNoNewArrayExpression;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("15.8")
@ParseletDefinition(
        name = "forsook.java.primaryExpression",
        emits = PrimaryExpression.class,
        needs = { PrimaryNoNewArrayExpression.class, ArrayCreationExpression.class }
)
public class PrimaryExpressionParselet extends JavaParselet<PrimaryExpression> {

    @Override
    public PrimaryExpression parse(Parser parser) {
        return (PrimaryExpression) parser.first(
                PrimaryNoNewArrayExpression.class, ArrayCreationExpression.class);
    }

}
