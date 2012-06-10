package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.2")
@ParseletDefinition(
        name = "forsook.java.hexadecimalFloatingPointLiteralExpression",
        emits = LiteralExpression.class
)
public class HexadecimalFloatingPointLiteralExpressionParselet 
        extends FloatingPointLiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        StringBuilder ret;
        if (!parser.peekPresentAndSkip('0')) {
            return null;
        } else if (parser.peekPresentAndSkip('x')) {
            ret = new StringBuilder("0x");
        } else if (parser.peekPresentAndSkip('X')) {
            ret = new StringBuilder("0X");
        } else  {
            return null;
        }
        boolean binaryExponentFound = false;
        boolean dotFound = false;
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if (chr >= 0 && chr <= 9) {
                ret.append(chr);
                parser.skip(1);
            } else if (((chr >= 'a' && chr <= 'f') ||
                    (chr >= 'A' && chr <= 'F')) && !binaryExponentFound) {
                ret.append(chr);
                parser.skip(1);
            } else if ((chr == 'p' || chr == 'P') && !binaryExponentFound) {
                ret.append(chr) ;
                parser.skip(1);
                binaryExponentFound = true;
                if (parser.peekPresentAndSkip('-')) {
                    ret.append('-');
                } else if (parser.peekPresentAndSkip('+')) {
                    ret.append('+');
                }
            } else if (chr == '.' && !dotFound) {
                ret.append('.');
                parser.skip(1);
                dotFound = true;
            } else {
                break;
            }
        } while (true);
        if (ret.length() == 0 || !binaryExponentFound) {
            return null;
        } else {
            checkEndOfFloatingPoint(parser, ret);
            return new LiteralExpression(ret.toString(), 
                    LiteralExpressionType.HEXADECIMAL_FLOATING_POINT);
        }
    }
}
