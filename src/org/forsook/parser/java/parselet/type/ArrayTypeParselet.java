package org.forsook.parser.java.parselet.type;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("4.3")
@ParseletDefinition(
        name = "forsook.java.arrayType",
        emits = ArrayType.class,
        needs = Type.class,
        recursiveMinimumSize = 2
)
public class ArrayTypeParselet extends TypeParselet<ArrayType> {

    @Override
    public ArrayType parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead('[')) {
            return null;
        }
        //type
        Type type = parser.next(Type.class);
        if (type == null) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //brackets
        parseWhiteSpaceAndComments(parser);
        if (!parser.peekPresentAndSkip('[')) {
            return type instanceof ArrayType ? (ArrayType) type : null;
        }
        parseWhiteSpaceAndComments(parser);
        if (!parser.peekPresentAndSkip(']')) {
            return type instanceof ArrayType ? (ArrayType) type : null;
        }
        return new ArrayType(type);
    }

}
