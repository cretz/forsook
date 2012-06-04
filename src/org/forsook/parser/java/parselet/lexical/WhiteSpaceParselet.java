package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.WhiteSpace;
import org.forsook.parser.java.ast.lexical.WhiteSpace.WhiteSpaceType;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("3.6")
@ParseletDefinition(
        name = "forsook.java.whiteSpace",
        emits = WhiteSpace.class
)
public class WhiteSpaceParselet extends JavaParselet<WhiteSpace> {

    @Override
    public WhiteSpace parse(Parser parser) {
        WhiteSpaceType type = null;
        int amount = 0;
        boolean previousWasReturn = false;
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            } else if (chr == ' ') {
                if (type == null) {
                    type = WhiteSpaceType.SPACE;
                }
                if (type == WhiteSpaceType.SPACE) {
                    amount++;
                    parser.skip(1);
                } else {
                    break;
                }
            } else if (chr == '\t') {
                if (type == null) {
                    type = WhiteSpaceType.TAB;
                }
                if (type == WhiteSpaceType.TAB) {
                    amount++;
                    parser.skip(1);
                } else {
                    break;
                }
            } else if (chr == '\f') {
                if (type == null) {
                    type = WhiteSpaceType.FORM_FEED;
                }
                if (type == WhiteSpaceType.FORM_FEED) {
                    amount++;
                    parser.skip(1);
                } else {
                    break;
                }
            } else if (chr == '\r' || chr == '\n') {
                if (type == null) {
                    type = chr == '\r' ? WhiteSpaceType.RETURN : WhiteSpaceType.NEWLINE;
                }
                if (chr == '\n' && amount == 1 && type == WhiteSpaceType.RETURN) {
                    //keep amount at 1
                    type = WhiteSpaceType.NEWLINE_RETURN;
                    parser.skip(1);
                } else if (chr == '\n' && type == WhiteSpaceType.NEWLINE_RETURN && previousWasReturn) {
                    previousWasReturn = false;
                    amount++;
                    parser.skip(1);
                } else if (chr == '\n' && type == WhiteSpaceType.NEWLINE) {
                    amount++;
                    parser.skip(1);
                } else if (chr == '\r' && type == WhiteSpaceType.RETURN) {
                    amount++;
                    parser.skip(1);
                } else if (chr == '\r' && type == WhiteSpaceType.NEWLINE_RETURN) {
                    previousWasReturn = true;
                    parser.skip(1);
                } else {
                    break;
                }
            } else {
                break;
            }
        } while (true);
        if (amount == 0) {
            return null;
        } else {
            return new WhiteSpace(type, amount);
        }
    }

}
