package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.ClassExpression;
import org.forsook.parser.java.ast.expression.ClassInstanceCreationExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.ParenthesizedExpression;
import org.forsook.parser.java.ast.expression.PrimaryNoNewArrayExpression;
import org.forsook.parser.java.ast.expression.ScopedExpression;
import org.forsook.parser.java.ast.expression.ThisExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
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
        Expression expr = (Expression) parser.first(
                ArrayAccessExpression.class,
                MethodInvocationExpression.class,
                FieldAccessExpression.class,
                ClassInstanceCreationExpression.class,
                ParenthesizedExpression.class,
                ThisExpression.class,
                ClassExpression.class,
                LiteralExpression.class
            );
        if (expr instanceof ArrayAccessExpression) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            if (parser.peekPresentAndSkip('.')) {
                //ok, if we got an array access expression...check for field/method
                ScopedExpression parent = (ScopedExpression) parser.first(
                        MethodInvocationExpression.class, FieldAccessExpression.class);
                if (parent == null) {
                    return null;
                }
                parent.setScope(expr);
                expr = (Expression) parent;
            }
        }
        if (expr instanceof MethodInvocationExpression) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            if (parser.peekPresentAndSkip('.')) {
                //ok, we got a method expression...check for field
                Object parent = parser.first(FieldAccessExpression.class, Identifier.class);
                if (parent == null) {
                    return null;
                } else if (parent instanceof Identifier) {
                    expr = new FieldAccessExpression(expr, null, false, (Identifier) parent);
                } else {
                    ((FieldAccessExpression) parent).setScope(expr);
                    expr = (Expression) parent;
                }
            }
        }
        return (PrimaryNoNewArrayExpression) expr;
    }

}
