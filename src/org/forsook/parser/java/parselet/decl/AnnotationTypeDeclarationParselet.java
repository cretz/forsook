package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.AnnotationTypeBody;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.parselet.packag.TypeDeclarationParselet;

@JlsReference("9.6")
@ParseletDefinition(
        name = "forsook.java.annotationTypeDeclaration",
        emits = AnnotationTypeDeclaration.class,
        needs = { Identifier.class, AnnotationTypeBody.class }
)
public class AnnotationTypeDeclarationParselet 
        extends TypeDeclarationParselet<AnnotationTypeDeclaration> {
    
    @Override
    public AnnotationTypeDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        List<Modifier> modifiers = new ArrayList<Modifier>();
        AtomicReference<JavadocComment> javadoc = new AtomicReference<JavadocComment>();
        if (!parseJavadocAndModifiers(parser, javadoc, annotations,
                modifiers, Modifier.PUBLIC, Modifier.ABSTRACT)) {
            return null;
        }
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
        //body
        AnnotationTypeBody body = parser.next(AnnotationTypeBody.class);
        if (body == null) {
            return null;
        }
        return new AnnotationTypeDeclaration(javadoc.get(), annotations, name, modifiers, body);
    }

}
