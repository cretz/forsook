package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.VariableDeclarationExpression;
import org.forsook.parser.java.ast.WhiteSpace;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("14.4")
@ParseletDefinition(
        name = "forsook.java.variableDeclarationExpression",
        emits = VariableDeclarationExpression.class,
        needs = {
            WhiteSpace.class,
            Comment.class,
            AnnotationExpression.class,
            Modifier.class,
            ReferenceType.class,
            VariableDeclarator.class
        }
)
public class VariableDeclarationExpressionParselet extends ExpressionParselet<VariableDeclarationExpression> {
    
    @Override
    @SuppressWarnings("unchecked")
    public VariableDeclarationExpression parse(Parser parser) {
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
        //type
        Type type = parser.next(ReferenceType.class);
        if (type == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //declarators
        List<VariableDeclarator> vars = new ArrayList<VariableDeclarator>();
        do {
            VariableDeclarator var = parser.next(VariableDeclarator.class);
            if (var != null) {
                vars.add(var);
            } else {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new VariableDeclarationExpression(javadoc, modifiers, annotations, type, vars);
    }

}
