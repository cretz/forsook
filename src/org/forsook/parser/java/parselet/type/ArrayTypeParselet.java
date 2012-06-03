package org.forsook.parser.java.parselet.type;

import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("4.3")
public class ArrayTypeParselet extends TypeParselet<ArrayType> {

    @Override
    public ArrayType parse(Parser parser) {
        //type
        Type type = parser.next(Type.class);
        if (type == null) {
            return null;
        }
        //brackets
        parseWhiteSpaceAndComments(parser);
        if (!parser.peekPresentAndSkip('[')) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        if (!parser.peekPresentAndSkip(']')) {
            return null;
        }
        return new ArrayType(type);
    }

}
