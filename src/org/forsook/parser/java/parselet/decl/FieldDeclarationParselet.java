package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("8.3")
@ParseletDefinition(
        name = "forsook.java.fieldDeclaration",
        emits = FieldDeclaration.class,
        needs = { Type.class, VariableDeclarator.class }
)
public class FieldDeclarationParselet extends BodyDeclarationParselet<FieldDeclaration> {

    @Override
    public FieldDeclaration parse(Parser parser) {
        //lookahead
        if (!parser.pushFirstDepthLookAhead(
                parser.peekAstDepth(), ';')) {
            return null;
        }
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        List<Modifier> modifiers = new ArrayList<Modifier>();
        AtomicReference<JavadocComment> javadoc = new AtomicReference<JavadocComment>();
        if (!parseJavadocAndModifiers(parser, javadoc, annotations,
                modifiers, Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE,
                Modifier.STATIC, Modifier.FINAL, Modifier.TRANSIENT, Modifier.VOLATILE)) {
            return null;
        }
        //type
        Type type = parser.next(Type.class);
        if (type == null) {
            return null;
        }
        //variables
        List<VariableDeclarator> variables = new ArrayList<VariableDeclarator>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //variable
            VariableDeclarator variable = parser.next(VariableDeclarator.class);
            if (variable == null) {
                return null;
            }
            variables.add(variable);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new FieldDeclaration(javadoc.get(), annotations, modifiers, type, variables);
    }

}
