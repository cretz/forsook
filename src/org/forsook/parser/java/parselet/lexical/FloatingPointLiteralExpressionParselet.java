package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.2")
@ParseletDefinition(
        name = "forsook.java.floatingPointLiteralExpression",
        emits = LiteralExpression.class
)
public class FloatingPointLiteralExpressionParselet extends LiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        String value = parseDecimalFloatingPointLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.DECIMAL_FLOATING_POINT);
        }
        value = parseHexadecimalFloatingPointLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.HEXADECIMAL_FLOATING_POINT);
        }
        return null;
    }
    
    private boolean checkEndOfFloatingPoint(Parser parser, StringBuilder ret) {
        Character chr = parser.peek();
        if (chr != null && (chr == 'f' || chr == 'F' || chr == 'd' || chr == 'D')) {
            ret.append(chr);
            parser.skip(1);
            return true;
        } else {
            return false;
        }
    }

    private String parseDecimalFloatingPointLiteral(Parser parser) {
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
            return ret.toString();
        }
    }
    
    private String parseHexadecimalFloatingPointLiteral(Parser parser) {
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
            return ret.toString();
        }
    }
}
