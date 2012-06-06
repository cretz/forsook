package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.NonWildTypeArguments;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.parselet.type.TypeArgumentsParselet;

@JlsReference("8.8.7.1")
@ParseletDefinition(
        name = "forsook.java.nonWildTypeArguments",
        emits = NonWildTypeArguments.class,
        needs = ReferenceType.class
)
public class NonWildTypeArgumentsParselet extends TypeArgumentsParselet {

    @Override
    public NonWildTypeArguments parse(Parser parser) {
        if (!parser.peekPresentAndSkip('<')) {
            return null;
        }
        List<Type> types = new ArrayList<Type>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //get type
            Type type = (Type) parser.next(ReferenceType.class);
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
        return new NonWildTypeArguments(types);
    }
}
