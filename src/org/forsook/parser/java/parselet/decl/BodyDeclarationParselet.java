package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
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

    /**
     * Parse the javadoc and the modifiers (including annotations). This handles
     * all whitespace on either side. If the result is false, parsing was invalid.
     * 
     * @param parser
     * @param javadoc
     * @param annotations
     * @param modifiers
     * @param allowedModifiers
     * @return
     */
    protected boolean parseJavadocAndModifiers(Parser parser, 
            AtomicReference<JavadocComment> javadoc,
            List<AnnotationExpression> annotations, 
            List<Modifier> modifiers, Modifier... allowedModifiers) {
        for (Object found : parser.any(WhiteSpace.class, Comment.class, 
                AnnotationExpression.class, Modifier.class)) {
            if (found instanceof JavadocComment) {
                javadoc.set((JavadocComment) found);
            } else if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            } else if (found instanceof Modifier) {
                Modifier modifier = (Modifier) found;
                //allowed?
                boolean foundModifier = false;
                for (Modifier allowedModifier : allowedModifiers) {
                    if (allowedModifier == modifier) {
                        foundModifier = true;
                        break;
                    }
                }
                if (!foundModifier) {
                    return false;
                }
                //already there?
                if (modifiers.contains(modifier)) {
                    return false;
                }
                modifiers.add(modifier); 
            }
        }
        return true;
    }
    
    /**
     * Parse the type parameters. This handles the &lt; and &gt;. This does NOT 
     * handle whitespace at the beginning, but does at the end.
     * 
     * @param parser
     * @return
     */
    protected List<TypeParameter> parseTypeParameters(Parser parser) {
        List<TypeParameter> typeParameters = new ArrayList<TypeParameter>();
        if (parser.peekPresentAndSkip('<')) {
            //lookahead
            if (!parser.pushLookAhead('>')) {
                return null;
            }
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
            //pop lookahead
            parser.popLookAhead();
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        return typeParameters;
    }
    
    /**
     * Parses parameters. This handles the parentheses. This does NOT
     * handle the whitespace at the beginning, but does at the end.
     * 
     * @param parser
     * @return
     */
    protected List<Parameter> parseParameters(Parser parser) {
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ')')) {
            return null;
        }
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
            parameters.add(parameter);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        return parameters;
    }
    
    /**
     * Parse the throws list. This handles the throws keyword. This does NOT
     * handle whitespace at the beginning, but does at the end.
     * 
     * @param parser
     * @return
     */
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
            } while (parser.peekPresentAndSkip(','));
        }
        return throwsList;
    }
}
