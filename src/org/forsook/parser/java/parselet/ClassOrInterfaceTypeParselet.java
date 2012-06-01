package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.TypeArguments;
import org.forsook.parser.java.ast.WildcardType;

@JlsReference("4.3")
@ParseletDefinition(
        name = "forsook.java.classOrInterfaceType",
        emits = ClassOrInterfaceType.class,
        needs = { 
            Identifier.class, 
            WildcardType.class, 
            ReferenceType.class,
            TypeArguments.class
        }
)
public class ClassOrInterfaceTypeParselet extends TypeParselet<ClassOrInterfaceType> {

    @Override
    public ClassOrInterfaceType parse(Parser parser) {
        ClassOrInterfaceType type = null;
        do {
            //name
            Identifier name = parser.next(Identifier.class);
            if (name == null) {
                //don't skip the dot, just get out (could be varargs)
                if (type == null) {
                    return null;
                } else {
                    break;
                }
            } else {
                parser.skip(1);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //type args
            TypeArguments typeArguments = parser.next(TypeArguments.class);
            //add
            type = new ClassOrInterfaceType(type, name, typeArguments);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresent('.'));
        return type;
    }

}
