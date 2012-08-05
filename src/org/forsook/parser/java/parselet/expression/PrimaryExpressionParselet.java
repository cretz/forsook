package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayCreationExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.expression.PrimaryNoNewArrayExpression;
import org.forsook.parser.java.ast.expression.ScopedExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("15.8")
@ParseletDefinition(
        name = "forsook.java.primaryExpression",
        emits = PrimaryExpression.class,
        needs = { PrimaryNoNewArrayExpression.class, ArrayCreationExpression.class }
)
public class PrimaryExpressionParselet<T extends PrimaryExpression> extends JavaParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        Expression expr = (Expression) parser.first(ArrayCreationExpression.class,
                PrimaryNoNewArrayExpression.class);
        return (T) checkExpressionTrailer(expr, parser);
    }

    protected Expression checkExpressionTrailer(Expression expr, Parser parser) {
        if (expr != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            int lastKnownGoodCursor = parser.getCursor();
            if (parser.peekPresentAndSkip('.')) {
                parseWhiteSpaceAndComments(parser);
                //ok, if we got something that may have right recursion...check for field/method
                Object parent = parser.next(MethodInvocationExpression.class);
                if (parent != null) {
                    ((ScopedExpression) parent).setScope(expr);
                    expr = (Expression) parent;
                    lastKnownGoodCursor = parser.getCursor();
                } else {
                    do {
                        parseWhiteSpaceAndComments(parser);
                        parent = parser.next(Identifier.class);
                        if (parent == null) {
                            break;
                        } else {
                            parseWhiteSpaceAndComments(parser);
                            lastKnownGoodCursor = parser.getCursor();
                            expr = new FieldAccessExpression(expr, null, false, (Identifier) parent);
                        }
                    } while (parser.peekPresentAndSkip('.'));
                }
                //rollback cursor
                int finalCursor = parser.getCursor();
                for (int i = 0; i < finalCursor - lastKnownGoodCursor; i++) {
                    //TODO: expose forced setter?
                    parser.backupCursor();
                }
            }
        }
        return expr;
    }
}
