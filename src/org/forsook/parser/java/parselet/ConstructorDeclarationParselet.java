package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.BlockStatement;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.ConstructorDeclaration;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.Parameter;
import org.forsook.parser.java.ast.TypeParameter;
import org.forsook.parser.java.ast.WhiteSpace;

@JlsReference("8.8")
@ParseletDefinition(
        name = "forsook.java.constructorDeclaration",
        emits = ConstructorDeclaration.class
)
public class ConstructorDeclarationParselet extends BodyDeclarationParselet<ConstructorDeclaration> {

    @Override
    public ConstructorDeclaration parse(Parser parser) {
        //TODO: stop copy/pasting from others (e.g. ClassDeclarationParselet)
        //annotations, javadoc, and modifiers
        JavadocComment javadoc = null;
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        int modifiers = 0;
        for (Object found : parser.any(WhiteSpace.class, Comment.class, 
                AnnotationExpression.class, Modifier.class)) {
            if (found instanceof JavadocComment) {
                javadoc = (JavadocComment) found;
            } else if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            } else if (found instanceof Modifier) {
                modifiers &= ((Modifier) found).getModifier();
            }
        }
        //type parameters
        List<TypeParameter> typeParameters = new ArrayList<TypeParameter>();
        if (parser.peekPresentAndSkip('<')) {
            do {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //type parameter
                TypeParameter typeParameter = parser.next(TypeParameter.class);
                if (typeParameter == null) {
                    return null;
                }
                typeParameters.add(typeParameter);
                //spacing
                parseWhiteSpaceAndComments(parser);
            } while (parser.peekPresentAndSkip(','));
            //spacing
            parseWhiteSpaceAndComments(parser);
            //end
            if (!parser.peekPresentAndSkip('>')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
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
        List<Parameter> parameters = new ArrayList<Parameter>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //parameter
            Parameter parameter = parser.next(Parameter.class);
            if (parameter == null) {
                if (parameters.isEmpty()) {
                    break;
                } else {
                    return null;
                }
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //throws
        List<ClassOrInterfaceType> throwsList = new ArrayList<ClassOrInterfaceType>();
        if (parser.peekPresentAndSkip("throws")) {
            do {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //type
                ClassOrInterfaceType throwsType = parser.next(ClassOrInterfaceType.class);
                if (throwsType == null) {
                    return null;
                }
                //spacing
                parseWhiteSpaceAndComments(parser);
            } while (!parser.peekPresentAndSkip(','));
        }
        //block
        BlockStatement block = parser.next(BlockStatement.class);
        if (block == null) {
            return null;
        }
        return new ConstructorDeclaration(javadoc, annotations, new Modifier(modifiers), 
                typeParameters, name, parameters, throwsList, block);
    }

}
