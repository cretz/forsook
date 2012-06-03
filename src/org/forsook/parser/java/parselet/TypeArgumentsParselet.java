package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.TypeArguments;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.WildcardType;

@JlsReference("4.5.1")
@ParseletDefinition(
        name = "forsook.java.typeArguments",
        emits = TypeArguments.class
)
public class TypeArgumentsParselet extends JavaParselet<TypeArguments> {

    @Override
    public TypeArguments parse(Parser parser) {
        if (!parser.peekPresentAndSkip('<')) {
            return null;
        }
        List<Type> types = new ArrayList<Type>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //get type
            Type type = parser.next(WildcardType.class);
            if (type == null) {
                type = parser.next(ReferenceType.class);
                if (type == null) {
                    //TODO: support Java 7 diamond 
                    return null;
                }
            }
            types.add(type);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        if (!parser.peekPresentAndSkip('>')) {
            return null;
        }
        return new TypeArguments(types);
    }

}
