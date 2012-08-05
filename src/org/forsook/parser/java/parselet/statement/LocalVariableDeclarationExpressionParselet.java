package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.parselet.expression.ExpressionParselet;

@JlsReference("14.4")
@ParseletDefinition(
        name = "forsook.java.localVariableDeclarationExpression",
        emits = LocalVariableDeclarationExpression.class,
        needs = {
            AnnotationExpression.class,
            Modifier.class,
            Type.class,
            VariableDeclarator.class
        }
)
public class LocalVariableDeclarationExpressionParselet extends ExpressionParselet<LocalVariableDeclarationExpression> {
    
    @Override
    public LocalVariableDeclarationExpression parse(Parser parser) {
        //annotations and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        boolean _final = false;
        do {
            Object found = parser.first(AnnotationExpression.class, Modifier.class);
            if (found instanceof Modifier) {
                if (((Modifier) found).getModifier() != java.lang.reflect.Modifier.FINAL) {
                    return null;
                } else {
                    _final = true;
                }
            } else if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            } else {
                break;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (true);
        //type
        Type type = parser.next(Type.class);
        if (type == null) {
            return null;
        }
        //declarators
        List<VariableDeclarator> vars = new ArrayList<VariableDeclarator>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //var
            VariableDeclarator var = parser.next(VariableDeclarator.class);
            if (var != null) {
                vars.add(var);
            } else {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        return new LocalVariableDeclarationExpression(_final, annotations, type, vars);
    }

}
