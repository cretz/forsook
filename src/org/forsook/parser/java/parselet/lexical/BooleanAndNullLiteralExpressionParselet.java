package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;
import org.forsook.parser.java.parselet.expression.ExpressionParselet;

@JlsReference({ "3.10.3", "3.10.7" })
@ParseletDefinition(
        name = "forsook.java.booleanAndNullLiteralExpression",
        emits = LiteralExpression.class
)
public class BooleanAndNullLiteralExpressionParselet extends ExpressionParselet<LiteralExpression> {
    
    @Override
    public LiteralExpression parse(Parser parser) {
        LiteralExpression expr;
        if (parser.peekPresentAndSkip("true")) {
            expr = new LiteralExpression("true", LiteralExpressionType.BOOLEAN);
        } else if (parser.peekPresentAndSkip("false")) {
            expr = new LiteralExpression("false", LiteralExpressionType.BOOLEAN);
        } else if (parser.peekPresentAndSkip("null")) {
            expr = new LiteralExpression("null", LiteralExpressionType.NULL);
        } else {
            return null;
        }
        //next char can't be a valid java identifier char
        if (parser.peek() != null && Character.isJavaIdentifierPart(parser.peek())) {
            return null;
        }
        return expr;
    }
}
