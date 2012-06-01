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
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.VariableDeclarator;
import org.forsook.parser.java.ast.WhiteSpace;

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
        JavadocComment javadoc = null;
        for (Object found : parser.any(WhiteSpace.class, 
                Comment.class, AnnotationExpression.class)) {
            if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            } else if (found instanceof JavadocComment) {
                javadoc = (JavadocComment) found;
            }
        }
        //modifiers
        int modifiers = 0;
        for (Object found : parser.any(WhiteSpace.class, Comment.class, Modifier.class)) {
            if (found instanceof Modifier) {
                modifiers &= ((Modifier) found).getModifier();
            }
        }
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
        return new FieldDeclaration(javadoc, annotations, 
                new Modifier(modifiers), type, variables);
    }

}
