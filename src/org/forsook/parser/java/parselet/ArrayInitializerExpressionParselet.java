package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ArrayInitializerExpression;
import org.forsook.parser.java.ast.Expression;

@JlsReference("10.6")
@ParseletDefinition(
        name = "forsook.java.arrayInitializerExpression",
        emits = ArrayInitializerExpression.class,
        needs = { ArrayInitializerExpression.class, Expression.class }
)
@ParseletDepends(VariableInitializerExpressionParselet.class)
public class ArrayInitializerExpressionParselet extends ExpressionParselet<ArrayInitializerExpression> {
    
    int[] i = { , };
    
    @Override
    public ArrayInitializerExpression parse(Parser parser) {
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        List<Expression> values = new ArrayList<Expression>();
        int commaCount = 0;
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //get the val
            Expression expression = parser.next(Expression.class);
            if (expression == null) {
                expression = parser.next(ArrayInitializerExpression.class);
            }
            if (expression != null) {
                values.add(expression);
            } else if (commaCount > 0) {
                //can't have "{ , , }" but can have "{ , }" 
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //comma?
            if (parser.peekPresentAndSkip(',')) {
                commaCount++;
            } else if (parser.peekPresentAndSkip('}')) {
                break;
            } else {
                return null;
            }
        } while (true);
        return new ArrayInitializerExpression(values);
    }

}
