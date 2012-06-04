package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.AnnotationTypeElementDeclaration;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;

@JlsReference("9.6")
@ParseletDefinition(
        name = "forsook.java.annotationTypeDeclaration",
        emits = AnnotationTypeDeclaration.class,
        needs = { Identifier.class, AnnotationTypeElementDeclaration.class }
)
public class AnnotationTypeDeclarationParselet 
        extends TypeDeclarationParselet<AnnotationTypeDeclaration> {
    
    @Override
    @SuppressWarnings("unchecked")
    public AnnotationTypeDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
        //interface/class
        if (!parser.peekPresentAndSkip("@interface")) {
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
        //brace
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //members
        List<BodyDeclaration> members = new ArrayList<BodyDeclaration>();
        do {
            //type elements
            List<BodyDeclaration> memberList = (List<BodyDeclaration>)
                    parser.any(AnnotationTypeElementDeclaration.class);
            if (memberList.isEmpty()) {
                //regular members
                memberList = parseTypeMembers(parser);
                if (memberList.isEmpty()) {
                    break;
                }
            }
            members.addAll(memberList);
        } while (true);
        //brace
        if (!parser.peekPresentAndSkip('}')) {
            return null;
        }
        return new AnnotationTypeDeclaration(javadoc, annotations, name, modifiers, members);
    }

}
