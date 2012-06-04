package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.Parameter;
import org.forsook.parser.java.ast.lexical.Comment;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.lexical.WhiteSpace;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.TypeParameter;
import org.forsook.parser.java.parselet.JavaParselet;

@ParseletDefinition(
        needs = {
                WhiteSpace.class,
                Comment.class,
                AnnotationExpression.class,
                Modifier.class,
                TypeParameter.class,
                Parameter.class,
                ClassOrInterfaceType.class
        }
)
public abstract class BodyDeclarationParselet<T extends BodyDeclaration> extends JavaParselet<T> {

    protected JavadocComment parseJavadocAndAnnotations(Parser parser, 
            List<AnnotationExpression> annotations) {
        JavadocComment javadoc = null;
        for (Object found : parser.any(WhiteSpace.class, Comment.class, 
                AnnotationExpression.class)) {
            if (found instanceof JavadocComment) {
                javadoc = (JavadocComment) found;
            } else if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            }
        }
        return javadoc;
    }
    
    protected Modifier parseModifiers(Parser parser) {
        Modifier modifier;
        int modifiers = 0;
        do {
           //spacing
           parseWhiteSpaceAndComments(parser);
           //modifier
           modifier = parser.next(Modifier.class);
        } while (modifier != null);
        return new Modifier(modifiers);
    }
    
    protected List<TypeParameter> parseTypeParameters(Parser parser) {
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
        return typeParameters;
    }
    
    protected List<Parameter> parseParameters(Parser parser) {
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
        return parameters;
    }
    
    protected List<ClassOrInterfaceType> parseThrows(Parser parser) {
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
        return throwsList;
    }
}
