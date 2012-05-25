package org.forsook.parser.java.parselet;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.Expression;

public abstract class ExpressionParselet<T extends Expression> extends JavaParselet<T> {

    protected Expression parseExpression(Parser parser) {
        //try conditional first
        Expression ret = parser.next(ConditionalExpressionParselet.class);
        if (ret == null) {
            //try assignment
            ret = parser.next(AssignmentExpressionParselet.class);
            if (ret == null) {
                return null;
            }
        }
        return ret;
    }
}
