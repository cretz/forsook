package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.Parser;

public abstract class IntegerLiteralExpressionParselet extends LiteralExpressionParselet {
    
    protected void checkEndingL(Parser parser, StringBuilder ret) {
        if (parser.peekPresentAndSkip('L')) {
            ret.append('L');
        } else if (parser.peekPresentAndSkip('l')) {
            ret.append('l');
        }
    }
}
