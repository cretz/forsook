package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;

@JlsReference("3.10.5")
@ParseletDefinition(
        name = "forsook.java.characterAndStringLiteralExpression",
        emits = LiteralExpression.class
)
public class CharacterAndStringLiteralExpressionParselet extends LiteralExpressionParselet {

    @Override
    public LiteralExpression parse(Parser parser) {
        if (parser.peekPresentAndSkip('\'')) {
            StringBuilder builder = parseStringOrCharacterPiece(parser, new StringBuilder("'"));
            if (!parser.peekPresentAndSkip('\'')) {
                return null;
            }
            return new LiteralExpression(builder.append('\'').toString(), LiteralExpressionType.CHARACTER);
        } else if (parser.peekPresentAndSkip('"')) {
            StringBuilder builder = new StringBuilder("\"");
            while (!parser.peekPresentAndSkip('"')) {
                if (parseStringOrCharacterPiece(parser, builder) == null) {
                    return null;
                }
            }
            return new LiteralExpression(builder.append('"').toString(), LiteralExpressionType.STRING);
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
}
