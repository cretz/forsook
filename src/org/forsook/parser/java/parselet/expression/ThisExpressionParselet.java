package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ThisExpression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.8.3")
@ParseletDefinition(
        name = "forsook.java.thisExpression",
        emits = ThisExpression.class,
        needs = QualifiedName.class
)
public class ThisExpressionParselet extends ExpressionParselet<ThisExpression> {

    @Override
    public ThisExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead("this")) {
            return null;
        }
        //name
        QualifiedName name = parser.next(QualifiedName.class);
        if (name != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //dot
            if (!parser.peekPresentAndSkip('.')) {
                return null;
            }
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //this
        if (!parser.peekPresentAndSkip("this")) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new ThisExpression(name);
    }

}
