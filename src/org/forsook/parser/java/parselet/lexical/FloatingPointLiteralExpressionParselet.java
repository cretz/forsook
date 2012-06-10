package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.Parser;

public abstract class FloatingPointLiteralExpressionParselet extends LiteralExpressionParselet {
    
    protected boolean checkEndOfFloatingPoint(Parser parser, StringBuilder ret) {
        Character chr = parser.peek();
        if (chr != null && (chr == 'f' || chr == 'F' || chr == 'd' || chr == 'D')) {
            ret.append(chr);
            parser.skip(1);
            return true;
        } else {
            return false;
        }
    }
}
