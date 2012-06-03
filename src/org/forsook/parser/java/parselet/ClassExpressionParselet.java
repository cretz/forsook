package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ClassExpression;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.VoidType;

@JlsReference("15.8.2")
@ParseletDefinition(
        name = "forsook.java.classExpression",
        emits = ClassExpression.class,
        needs = { ReferenceType.class, VoidType.class }
)
public class ClassExpressionParselet extends ExpressionParselet<ClassExpression> {

    @Override
    public ClassExpression parse(Parser parser) {
        //type
        Type type = (Type) parser.first(ReferenceType.class, VoidType.class);
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
        return new ClassExpression(type);
    }
}
