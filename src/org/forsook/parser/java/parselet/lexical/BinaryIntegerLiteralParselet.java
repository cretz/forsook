package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.1")
@ParseletDefinition(
        name = "forsook.java.binaryIntegerLiteralExpression",
        emits = LiteralExpression.class
)
public class BinaryIntegerLiteralParselet extends IntegerLiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        StringBuilder ret;
        if (!parser.peekPresentAndSkip('0')) {
            return null;
        } else if (parser.peekPresentAndSkip('b')) {
            ret = new StringBuilder("0b");
        } else if (parser.peekPresentAndSkip('B')) {
            ret = new StringBuilder("0B");
        } else  {
            return null;
        }
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if (chr == '0' || chr == '1' || chr == '_') {
                ret.append(chr);
                parser.skip(1);
            } else {
                break;
            }
        } while (true);
        if (ret.length() <= 3) {
            return null;
        } else if (ret.charAt(2) == '_' || ret.charAt(ret.length() - 1) == '_') {
            return null;
        } else {
            checkEndingL(parser, ret);
            return new LiteralExpression(ret.toString(), LiteralExpressionType.BINARY_INTEGER);
        }
    }
}
