package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.1")
@ParseletDefinition(
        name = "forsook.java.octalIntegerLiteralExpression",
        emits = LiteralExpression.class
)
public class OctalIntegerLiteralParselet extends IntegerLiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        if (!parser.peekPresentAndSkip('0')) {
            return null;
        }
        StringBuilder ret = new StringBuilder("0");
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if ((chr >= '0' && chr <= '7') || chr == '_') {
                ret.append(chr);
                parser.skip(1);
            } else {
                break;
            }
        } while (true);
        if (ret.length() <= 1) {
            return null;
        } else if (ret.charAt(1) == '_' || ret.charAt(ret.length() - 1) == '_') {
            return null;
        } else {
            checkEndingL(parser, ret);
            return new LiteralExpression(ret.toString(), LiteralExpressionType.OCTAL_INTEGER);
        }
    }
}
