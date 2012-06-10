package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.2")
@ParseletDefinition(
        name = "forsook.java.decimalFloatingPointLiteralExpression",
        emits = LiteralExpression.class
)
public class DecimalFloatingPointLiteralExpressionParselet 
        extends FloatingPointLiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        boolean dotFound = false;
        boolean eFound = false;
        StringBuilder ret = new StringBuilder();
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if (chr >= '0' && chr <= '9') {
                ret.append(chr);
                parser.skip(1);
            } else if ((chr == 'e' || chr == 'E') && !eFound) {
                ret.append(chr);
                parser.skip(1);
                eFound = true;
                if (parser.peekPresentAndSkip('-')) {
                    ret.append('-');
                } else if (parser.peekPresentAndSkip('+')) {
                    ret.append('+');
                }
            } else if (chr == '.' && !dotFound && !eFound) {
                ret.append('.');
                parser.skip(1);
                dotFound = true;
            } else {
                break;
            }
        } while (true);
        if (ret.length() == 0) {
            return null;
        } else if (ret.charAt(ret.length() - 1) == 'E' || ret.charAt(ret.length() - 1) == 'e') {
            return null;
        } else {
            boolean hasEnd = checkEndOfFloatingPoint(parser, ret);
            if (!dotFound && !eFound && !hasEnd) {
                return null;
            }
            return new LiteralExpression(ret.toString(), LiteralExpressionType.DECIMAL_FLOATING_POINT);
        }
    }
}
