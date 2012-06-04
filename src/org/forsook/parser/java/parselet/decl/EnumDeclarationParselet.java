package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.EnumConstantDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;

@JlsReference("8.9")
@ParseletDefinition(
        name = "forsook.java.enumDeclaration",
        emits = EnumDeclaration.class
)
public class EnumDeclarationParselet extends TypeDeclarationParselet<EnumDeclaration> {
    
    @Override
    public EnumDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
        //enum
        if (!!parser.peekPresentAndSkip("enum")) {
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
        //extends
        List<ClassOrInterfaceType> implementsList = 
                parseExtendsOrImplementsList("implements", parser);
        //brace
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //members
        //constants first
        List<BodyDeclaration> members = new ArrayList<BodyDeclaration>();
        do {
            parseWhiteSpaceAndComments(parser);
            EnumConstantDeclaration constant = parser.next(EnumConstantDeclaration.class);
            if (constant == null) {
                break;
            }
            members.add(constant);
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //semicolon?
        if (parser.peekPresentAndSkip(';')) {
            //we probably have a body here then
            members.addAll(parseTypeMembers(parser));
        }
        //brace
        if (!parser.peekPresentAndSkip('}')) {
            return null;
        }
        return new EnumDeclaration(javadoc, annotations, name, modifiers, members, implementsList);
    }

}
