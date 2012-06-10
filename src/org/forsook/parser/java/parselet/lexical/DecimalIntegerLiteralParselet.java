package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.1")
@ParseletDefinition(
        name = "forsook.java.decimalIntegerLiteralExpression",
        emits = LiteralExpression.class
)
public class DecimalIntegerLiteralParselet extends IntegerLiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        StringBuilder ret = new StringBuilder();
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if ((chr >= '0' && chr <= '9') || chr == '_') {
                ret.append(chr);
                parser.skip(1);
            } else {
                break;
            }
        } while (true);
        if (ret.length() == 0) {
            return null;
        } else if ((ret.charAt(0) == '0' && ret.length() > 1) || ret.charAt(0) == '_' ||
                ret.charAt(ret.length() - 1) == '_') {
            //can't be octal
            return null;
        } else if (ret.charAt(0) == '0' && (parser.peekPresent('b') ||
                parser.peekPresent('B') || parser.peekPresent('x') || parser.peekPresent('X'))) {
            //can't be binary or hex
            return null;
        } else {
            checkEndingL(parser, ret);
            return new LiteralExpression(ret.toString(), LiteralExpressionType.DECIMAL_INTEGER);
        }
    }
}
