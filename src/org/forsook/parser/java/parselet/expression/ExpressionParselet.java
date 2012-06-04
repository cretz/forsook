package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.ConditionalExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("15.27")
@ParseletDefinition(
        name = "forsook.java.expression",
        emits = Expression.class,
        needs = { ConditionalExpression.class, AssignmentExpression.class }
)
public class ExpressionParselet<T extends Expression> extends JavaParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        //try conditional first
        Expression ret = parser.next(ConditionalExpression.class);
        if (ret == null) {
            //try assignment
            ret = parser.next(AssignmentExpression.class);
            if (ret == null) {
                return null;
            }
        }
        return (T) ret;
    }
}
