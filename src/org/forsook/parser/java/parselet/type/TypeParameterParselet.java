package org.forsook.parser.java.parselet.type;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.TypeParameter;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("4.4")
@ParseletDefinition(
        name = "forsook.java.typeParameter",
        emits = TypeParameter.class,
        needs = { Identifier.class, ClassOrInterfaceType.class }
)
public class TypeParameterParselet extends JavaParselet<TypeParameter> {

    @Override
    public TypeParameter parse(Parser parser) {
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type bound
        List<ClassOrInterfaceType> typesBound = new ArrayList<ClassOrInterfaceType>();
        if (parser.peekPresentAndSkip("extends")) {
            do {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //type
                ClassOrInterfaceType type = parser.next(ClassOrInterfaceType.class);
                if (type == null) {
                    return null;
                }
                typesBound.add(type);
                //spacing
                parseWhiteSpaceAndComments(parser);
            } while (parser.peekPresentAndSkip('&'));
        }
        return new TypeParameter(name, typesBound);
    }

}
