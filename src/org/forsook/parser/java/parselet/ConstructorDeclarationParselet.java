package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.BlockStatement;
import org.forsook.parser.java.ast.ConstructorDeclaration;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.Parameter;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.TypeParameter;

@JlsReference("8.8")
@ParseletDefinition(
        name = "forsook.java.constructorDeclaration",
        emits = ConstructorDeclaration.class
)
public class ConstructorDeclarationParselet extends BodyDeclarationParselet<ConstructorDeclaration> {

    @Override
    public ConstructorDeclaration parse(Parser parser) {
        //javadoc and annotations
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
        //type parameters
        List<TypeParameter> typeParameters = parseTypeParameters(parser);
        if (typeParameters == null) {
            return null;
        }
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //parameters
        List<Parameter> parameters = parseParameters(parser);
        if (parameters == null) {
            return null;
        }
        //throws
        List<ClassOrInterfaceType> throwsList = parseThrows(parser);
        if (throwsList == null) {
            return null;
        }
        //block
        BlockStatement block = parser.next(BlockStatement.class);
        if (block == null) {
            return null;
        }
        return new ConstructorDeclaration(javadoc, annotations, modifiers, 
                typeParameters, name, parameters, throwsList, block);
    }

}
