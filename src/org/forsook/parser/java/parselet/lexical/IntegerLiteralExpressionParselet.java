package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.1")
@ParseletDefinition(
        name = "forsook.java.integerLiteralExpression",
        emits = LiteralExpression.class
)
public class IntegerLiteralExpressionParselet extends LiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        //gotta reset cursor when not found
        int cursor = parser.getCursor();
        String value = parseHexIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.HEX_INTEGER);
        }
        parser.setCursor(cursor);
        value = parseOctalIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.OCTAL_INTEGER);
        }
        parser.setCursor(cursor);
        value = parseBinaryIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.BINARY_INTEGER);
        }
        parser.setCursor(cursor);
        //check this last
        value = parseDecimalIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.DECIMAL_INTEGER);
        }
        return null;
    }
    
    private void checkEndingL(Parser parser, StringBuilder ret) {
        if (parser.peekPresentAndSkip('L')) {
            ret.append('L');
        } else if (parser.peekPresentAndSkip('l')) {
            ret.append('l');
        }
    }
    
    private String parseDecimalIntegerLiteral(Parser parser) {
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
            return null;
        } else {
            checkEndingL(parser, ret);
            return ret.toString();
        }
    }
    
    private String parseHexIntegerLiteral(Parser parser) {
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
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if ((chr >= '0' && chr <= '9') || 
                    (chr >= 'a' && chr <= 'f') ||
                    (chr >= 'A' && chr <= 'F') || chr == '_') {
                ret.append(chr);
                parser.skip(1);
            } else {
                break;
            }
        } while (true);
        if (ret.length() <= 2) {
            return null;
        } else if (ret.charAt(2) == '_' || ret.charAt(ret.length() - 1) == '_') {
            return null;
        } else {
            checkEndingL(parser, ret);
            return ret.toString();
        }
    }
    
    private String parseOctalIntegerLiteral(Parser parser) {
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
            return ret.toString();
        }
    }
    
    private String parseBinaryIntegerLiteral(Parser parser) {
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
            return ret.toString();
        }
    }
}
