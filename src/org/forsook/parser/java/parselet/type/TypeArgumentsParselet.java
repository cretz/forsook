package org.forsook.parser.java.parselet.type;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.TypeArguments;
import org.forsook.parser.java.ast.type.WildcardType;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("4.5.1")
@ParseletDefinition(
        name = "forsook.java.typeArguments",
        emits = TypeArguments.class,
        needs = { ReferenceType.class, WildcardType.class }
)
public class TypeArgumentsParselet extends JavaParselet<TypeArguments> {

    @Override
    public TypeArguments parse(Parser parser) {
        if (!parser.peekPresentAndSkip('<')) {
            return null;
        }
        //lookahead
        if (!parser.pushLookAhead('>')) {
            return null;
        }
        List<Type> types = new ArrayList<Type>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //get type
            Type type = (Type) parser.first(ReferenceType.class, WildcardType.class);
            if (type == null) { 
                return null;
            }
            types.add(type);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        if (!parser.peekPresentAndSkip('>')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new TypeArguments(types);
    }

}
