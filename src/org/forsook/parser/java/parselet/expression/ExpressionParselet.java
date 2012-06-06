package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("15.27")
@ParseletDefinition(
        name = "forsook.java.expression",
        emits = Expression.class,
        needs = AssignmentExpression.class
)
public class ExpressionParselet<T extends Expression> extends JavaParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.next(AssignmentExpression.class);
    }
}
