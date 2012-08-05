package org.forsook.parser.java.parselet.array;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.array.ArrayInitializerExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.parselet.expression.ExpressionParselet;

@JlsReference("10.6")
@ParseletDefinition(
        name = "forsook.java.arrayInitializerExpression",
        emits = ArrayInitializerExpression.class,
        needs = { ArrayInitializerExpression.class, Expression.class }
)
public class ArrayInitializerExpressionParselet 
        extends ExpressionParselet<ArrayInitializerExpression> {
    
    @Override
    public ArrayInitializerExpression parse(Parser parser) {
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), '}')) {
            return null;
        }
        //values
        List<Expression> values = new ArrayList<Expression>();
        boolean trailingComma = false;
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //get the val
            Expression expression = parser.next(Expression.class);
            if (expression == null) {
                expression = parser.next(ArrayInitializerExpression.class);
            }
            if (expression != null) {
                trailingComma = false;
                values.add(expression);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //comma?
            if (parser.peekPresentAndSkip(',')) {
                trailingComma = true;
            } else if (parser.peekPresentAndSkip('}')) {
                //pop lookahead
                parser.popLookAhead();
                break;
            } else {
                return null;
            }
        } while (true);
        return new ArrayInitializerExpression(values, trailingComma);
    }

}
