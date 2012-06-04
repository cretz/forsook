package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.statement.Resource;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.20.3")
@ParseletDefinition(
        name = "forsook.java.resource",
        emits = Resource.class,
        needs = {
            AnnotationExpression.class,
            Modifier.class,
            Type.class,
            VariableDeclaratorId.class,
            Expression.class
        }
)
public class ResourceParselet extends JavaParselet<Resource> {

    @Override
    public Resource parse(Parser parser) {
        //annotations and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        Modifier modifiers = null;
        do {
            Object object = parser.first(AnnotationExpression.class, Modifier.class);
            if (object instanceof Modifier) {
                if (((Modifier) object).getModifier() != java.lang.reflect.Modifier.FINAL) {
                    return null;
                }
                modifiers = (Modifier) object;
            } else if (object instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) object);
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
        //spacing
        parseWhiteSpaceAndComments(parser);
        //name
        VariableDeclaratorId name = parser.next(VariableDeclaratorId.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //equals
        if (!parser.peekPresentAndSkip('=')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = parser.next(Expression.class);
        return new Resource(modifiers, type, name, expression);
    }

}
