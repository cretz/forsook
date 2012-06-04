package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.TypeParameter;

@JlsReference({ "8.1", "9.1" })
@ParseletDefinition(
        name = "forsook.java.classOrInterfaceDeclaration",
        emits = ClassOrInterfaceDeclaration.class,
        needs = { Identifier.class, ClassOrInterfaceBody.class }
)
public class ClassOrInterfaceDeclarationParselet extends TypeDeclarationParselet<ClassOrInterfaceDeclaration> {
    
    @Override
    public ClassOrInterfaceDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
        //interface/class
        boolean iface = parser.peekPresentAndSkip("interface");
        if (!iface && !parser.peekPresentAndSkip("class")) {
            return null;
        }
        //some spacing required
        if (parseWhiteSpaceAndComments(parser).isEmpty()) {
            return null;
        }
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        //type parameters
        List<TypeParameter> parameters = parseTypeParameters(parser);
        parseWhiteSpaceAndComments(parser);
        //extends
        List<ClassOrInterfaceType> extendsList = 
                parseExtendsOrImplementsList("extends", parser);
        //implements
        List<ClassOrInterfaceType> implementsList = 
                parseExtendsOrImplementsList("implements", parser);
        //body
        ClassOrInterfaceBody body = parser.next(ClassOrInterfaceBody.class);
        if (body == null) {
            return null;
        }
        return new ClassOrInterfaceDeclaration(javadoc, annotations, name, modifiers, 
                body, iface, parameters, extendsList, implementsList);
    }

}
