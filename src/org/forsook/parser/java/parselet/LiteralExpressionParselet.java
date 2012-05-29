package org.forsook.parser.java.parselet;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.LiteralExpression;
import org.forsook.parser.java.ast.LiteralExpression.LiteralExpressionType;

public class LiteralExpressionParselet extends ExpressionParselet<LiteralExpression> {

    @Override
    public LiteralExpression parse(Parser parser) {
        LiteralExpression expr = parseIntegerLiteral(parser);
        if (expr == null) {
            expr = parseFloatingPointLiteral(parser);
            if (expr == null) {
                expr = parseBooleanLiteral(parser);
                if (expr == null) {
                    expr = parseCharacterLiteral(parser);
                    if (expr == null) {
                        expr = parseStringLiteral(parser);
                        if (expr == null) {
                            expr = parseNullLiteral(parser);
                        }
                    }
                }
            }
        }
        return expr;
    }

    private LiteralExpression parseIntegerLiteral(Parser parser) {
        String value = parseDecimalIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.DECIMAL_INTEGER);
        }
        value = parseHexIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.HEX_INTEGER);
        }
        value = parseOctalIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.OCTAL_INTEGER);
        }
        value = parseBinaryIntegerLiteral(parser);
        if (value != null) {
            return new LiteralExpression(value, LiteralExpressionType.BINARY_INTEGER);
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
        } else if ((ret.charAt(0) == '0' && ret.length() > 1) || ret.charAt(0) == '_') {
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
        if (ret.length() == 0) {
            return null;
        } else if (ret.charAt(2) == '_') {
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
        if (ret.length() == 0) {
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
        if (ret.length() == 0) {
            return null;
        } else if (ret.charAt(2) == '_') {
            return null;
        } else {
            checkEndingL(parser, ret);
            return ret.toString();
        }
    }

    private LiteralExpression parseFloatingPointLiteral(Parser parser) {
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
    
    private void checkEndOfFloatingPoint(Parser parser, StringBuilder ret) {
        Character chr = parser.peek();
        if (chr != null && (chr == 'f' || chr == 'F' || chr == 'd' || chr == 'D')) {
            ret.append(chr);
            parser.skip(1);
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
            } else if (chr == '.' && !dotFound) {
                ret.append('.');
                parser.skip(1);
                dotFound = true;
            } else {
                break;
            }
        } while (true);
        if (ret.length() == 0) {
            return null;
        } else {
            checkEndOfFloatingPoint(parser, ret);
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
        if (ret.length() == 0) {
            return null;
        } else {
            checkEndOfFloatingPoint(parser, ret);
            return ret.toString();
        }
    }

    private LiteralExpression parseBooleanLiteral(Parser parser) {
        if (parser.peekPresentAndSkip("true")) {
            return new LiteralExpression("true", LiteralExpressionType.BOOLEAN);
        } else if (parser.peekPresentAndSkip("false")) {
            return new LiteralExpression("false", LiteralExpressionType.BOOLEAN);
        } else {
            return null;
        }
    }
    
    private StringBuilder parseStringOrCharacterPiece(Parser parser, StringBuilder builder) {
        //we're just gonna keep it verbatim for now
        Character chr = parser.peek();
        if (chr == null || chr == '\n' || chr == '\r') {
            return null;
        } else if (chr != '\\') {
            //no escape sequence?
            builder.append(chr);
            parser.skip(1);
        } else {
            //ok, we got an escape
            builder.append(chr);
            parser.skip(1);
            //normal?
            chr = parser.peek();
            if (chr == null) {
                return null;
            } else if (chr == 'b' || chr == 't' ||
                    chr == 'n' || chr == 'f' || chr == 'r' ||
                    chr == '"' || chr == '\'' || chr == '\\') {
                builder.append(chr);
                parser.skip(1);
            } else if (chr >= '0' && chr <= '7') {
                //octal?
                char firstChar = chr;
                builder.append(chr);
                parser.skip(1);
                chr = parser.peek();
                if (chr >= '0' && chr <= '7') {
                    builder.append(chr);
                    parser.skip(1);
                    chr = parser.peek();
                    if (chr >= '0' && chr <= '7' && firstChar <= '3') {
                        builder.append(chr);
                        parser.skip(1);
                    }
                }
            } else if (chr == 'u') {
                //unicode?
                do {
                    builder.append(chr);
                    parser.skip(1);
                    chr = parser.peek();
                } while (chr == 'u');
                int i;
                for (i = 0; i < 4; i++) {
                    chr = parser.peek();
                    if ((chr >= '0' && chr <= '9') ||
                            (chr >= 'a' && chr <= 'f') ||
                            (chr >= 'A' && chr <= 'F')) {
                        builder.append(chr);
                        parser.skip(1);
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }
        return builder;
    }

    private LiteralExpression parseCharacterLiteral(Parser parser) {
        if (!parser.peekPresentAndSkip('\'')) {
            return null;
        }
        StringBuilder builder = parseStringOrCharacterPiece(parser, new StringBuilder("'"));
        if (!parser.peekPresentAndSkip('\'')) {
            return null;
        }
        return new LiteralExpression(builder.append('\'').toString(), LiteralExpressionType.CHARACTER);
    }

    private LiteralExpression parseStringLiteral(Parser parser) {
        if (!parser.peekPresentAndSkip('"')) {
            return null;
        }
        StringBuilder builder = new StringBuilder("\"");
        while (!parser.peekPresentAndSkip('"')) {
            if (parseStringOrCharacterPiece(parser, builder) == null) {
                return null;
            }
        }
        return new LiteralExpression(builder.append('"').toString(), LiteralExpressionType.STRING);
    }

    private LiteralExpression parseNullLiteral(Parser parser) {
        if (!parser.peekPresentAndSkip("null")) {
            return null;
        }
        return new LiteralExpression("null", LiteralExpressionType.NULL);
    }
}
