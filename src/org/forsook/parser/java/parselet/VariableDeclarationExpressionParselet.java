package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Type;
import org.forsook.parser.java.ast.VariableDeclarationExpression;
import org.forsook.parser.java.ast.VariableDeclarator;
import org.forsook.parser.java.ast.VariableDeclaratorId;

public class VariableDeclarationExpressionParselet extends ExpressionParselet<VariableDeclarationExpression> {

    @Override
    @SuppressWarnings("unchecked")
    public VariableDeclarationExpression parse(Parser parser) {
        //annotations, javadoc, and modifiers
        JavadocComment javadoc = null;
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        int modifiers = 0;
        for (Object found : parser.any(WhiteSpaceParselet.class, CommentParselet.class,
                AnnotationExpressionParselet.class, ModifierParselet.class)) {
            if (found instanceof JavadocComment) {
                javadoc = (JavadocComment) found;
            } else if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            } else if (found instanceof Integer) {
                modifiers &= (Integer) found;
            }
        }
        //type
        Type type = parser.next(ReferenceTypeParselet.class);
        if (type == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //declarators
        List<VariableDeclarator> vars = new ArrayList<VariableDeclarator>();
        do {
            //name
            String name = parser.next(IdentifierParselet.class);
            if (name == null) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //array count
            int arrayCount = 0;
            do {
                parseWhiteSpaceAndComments(parser);
                if (!parser.peekPresentAndSkip('[')) {
                    break;
                }
                parseWhiteSpaceAndComments(parser);
                if (!parser.peekPresentAndSkip(']')) {
                    return null;
                }
                arrayCount++;
            } while (true);
            //initializer?
            Expression init = null;
            if (parser.peekPresentAndSkip('=')) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //array initializer?
                //TODO
            }
            vars.add(new VariableDeclarator(new VariableDeclaratorId(name, arrayCount), init));
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (!parser.peekPresentAndSkip(','));
        if (vars.isEmpty()) {
            return null;
        }
        return new VariableDeclarationExpression(javadoc, modifiers, annotations, type, vars);
    }

}
