package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.LiteralExpression;
import org.forsook.parser.java.ast.LiteralExpression.LiteralExpressionType;

@JlsReference({ "3.10.3", "3.10.7" })
@ParseletDefinition(
        name = "forsook.java.booleanAndNullLiteralExpression",
        emits = LiteralExpression.class
)
public class BooleanAndNullLiteralExpressionParselet extends ExpressionParselet<LiteralExpression> {
    
    @Override
    public LiteralExpression parse(Parser parser) {
        if (parser.peekPresentAndSkip("true")) {
            return new LiteralExpression("true", LiteralExpressionType.BOOLEAN);
        } else if (parser.peekPresentAndSkip("false")) {
            return new LiteralExpression("false", LiteralExpressionType.BOOLEAN);
        } else if (parser.peekPresentAndSkip("null")) {
            return new LiteralExpression("null", LiteralExpressionType.NULL);
        } else {
            return null;
        }
    }
}
