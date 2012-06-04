package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
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
        //annotations and javadoc
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
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
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new FieldDeclaration(javadoc, annotations, modifiers, type, variables);
    }

}
