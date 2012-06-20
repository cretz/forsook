package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ClassExpression;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.VoidType;

@JlsReference("15.8.2")
@ParseletDefinition(
        name = "forsook.java.classExpression",
        emits = ClassExpression.class,
        needs = { Type.class, VoidType.class }
)
public class ClassExpressionParselet extends ExpressionParselet<ClassExpression> {

    @Override
    public ClassExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead("class")) {
            return null;
        }
        //type
        Type type = (Type) parser.first(Type.class, VoidType.class);
        if (type == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //dot
        if (!parser.peekPresentAndSkip('.')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //class keyword
        if (!parser.peekPresentAndSkip("class")) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new ClassExpression(type);
    }
}
