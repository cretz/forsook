package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.FieldDeclaration;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.VariableDeclarator;
import org.forsook.parser.java.ast.WhiteSpace;
import org.forsook.parser.java.ast.type.ReferenceType;

@JlsReference("8.3")
@ParseletDefinition(
        name = "forsook.java.fieldDeclaration",
        emits = FieldDeclaration.class
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
        ReferenceType type = parser.next(ReferenceType.class);
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
