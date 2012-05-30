package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.Type;
import org.forsook.parser.java.ast.WildcardType;

@JlsReference("4.3")
@ParseletDefinition(
        name = "forsook.java.classOrInterfaceType",
        emits = ClassOrInterfaceType.class,
        needs = { Identifier.class, WildcardType.class, ReferenceType.class }
)
public class ClassOrInterfaceTypeParselet extends TypeParselet<ClassOrInterfaceType> {

    @Override
    public ClassOrInterfaceType parse(Parser parser) {
        ClassOrInterfaceType type = null;
        do {
            //name
            Identifier name = parser.next(Identifier.class);
            if (name == null) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //type args
            List<Type> typeArguments = parseTypeArguments(parser);
            if (typeArguments == null) {
                return null;
            }
            //add
            type = new ClassOrInterfaceType(type, name, typeArguments);
        } while (parser.peekPresentAndSkip('.'));
        return type;
    }

    private List<Type> parseTypeArguments(Parser parser) {
        if (!parser.peekPresentAndSkip('<')) {
            return new ArrayList<Type>(0);
        }
        List<Type> ret = new ArrayList<Type>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //get type
            Type type = parser.next(WildcardType.class);
            if (type == null) {
                type = parser.next(ReferenceType.class);
                if (type == null) {
                    return null;
                }
            }
            ret.add(type);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        return parser.peekPresentAndSkip('>') ? ret : null; 
    }

}
