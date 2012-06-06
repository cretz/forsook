package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.AnnotationTypeElementDeclaration;
import org.forsook.parser.java.ast.decl.ElementValue;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("9.6.1")
@ParseletDefinition(
        name = "forsook.java.annotationTypeElementDeclaration",
        emits = AnnotationTypeElementDeclaration.class,
        needs = { Type.class, Identifier.class, ElementValue.class }
)
public class AnnotationTypeElementDeclarationParselet 
        extends BodyDeclarationParselet<AnnotationTypeElementDeclaration> {

    @Override
    public AnnotationTypeElementDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
        //type
        Type type = parser.next(Type.class);
        if (type == null) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        //TODO: dims
        //default
        ElementValue defaultValue = null;
        if (parser.peekPresentAndSkip("default")) {
            parseWhiteSpaceAndComments(parser);
            defaultValue = parser.next(ElementValue.class);
            if (defaultValue == null) {
                return null;
            }
            parseWhiteSpaceAndComments(parser);
        }
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new AnnotationTypeElementDeclaration(javadoc, annotations, 
                modifiers, type, name, defaultValue);
    }

}
