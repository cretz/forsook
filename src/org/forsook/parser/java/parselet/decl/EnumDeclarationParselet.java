package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.EnumBody;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.parselet.packag.TypeDeclarationParselet;

@JlsReference("8.9")
@ParseletDefinition(
        name = "forsook.java.enumDeclaration",
        emits = EnumDeclaration.class,
        needs = { Identifier.class, EnumBody.class }
)
public class EnumDeclarationParselet extends TypeDeclarationParselet<EnumDeclaration> {
    
    @Override
    public EnumDeclaration parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead("enum")) {
            return null;
        }
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        List<Modifier> modifiers = new ArrayList<Modifier>();
        AtomicReference<JavadocComment> javadoc = new AtomicReference<JavadocComment>();
        if (!parseJavadocAndModifiers(parser, javadoc, annotations,
                modifiers, Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE,
                Modifier.ABSTRACT, Modifier.STATIC, Modifier.FINAL, Modifier.STRICTFP)) {
            return null;
        }
        //enum
        if (!parser.peekPresentAndSkip("enum")) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
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
        //body
        EnumBody body = parser.next(EnumBody.class);
        if (body == null) {
            return null;
        }
        return new EnumDeclaration(javadoc.get(), annotations, name, 
                modifiers, body, implementsList);
    }

}
