package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ThisExpression;

@JlsReference("15.8.3")
@ParseletDefinition(
        name = "forsook.java.thisExpression",
        emits = ThisExpression.class
)
public class ThisExpressionParselet extends ExpressionParselet<ThisExpression> {

    @Override
    public ThisExpression parse(Parser parser) {
        if (!parser.peekPresentAndSkip("this")) {
            return null;
        }
        return new ThisExpression();
    }

}
