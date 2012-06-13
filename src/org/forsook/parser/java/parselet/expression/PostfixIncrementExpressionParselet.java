package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PostfixExpression;
import org.forsook.parser.java.ast.expression.PostfixIncrementExpression;

@JlsReference({ "15.14.2", "15.14.3" })
@ParseletDefinition(
        name = "forsook.java.postfixIncrementExpression",
        emits = PostfixIncrementExpression.class,
        needs = PostfixExpression.class,
        recursiveMinimumSize = 2
)
public class PostfixIncrementExpressionParselet extends ExpressionParselet<PostfixIncrementExpression> {

    @Override
    public PostfixIncrementExpression parse(Parser parser) {
        //lookahead
        if (!parser.lookAhead("++", "--")) {
            return null;
        }
        //expression
        Expression expression = (Expression) parser.next(PostfixExpression.class);
        if (expression == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //increment
        boolean increment = parser.peekPresentAndSkip("++");
        if (!increment && !parser.peekPresentAndSkip("--")) {
            return null;
        }
        return new PostfixIncrementExpression(expression, increment);
    }
}
