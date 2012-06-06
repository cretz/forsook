package org.forsook.parser.java.parselet.expression;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.array.ArrayInitializerExpression;
import org.forsook.parser.java.ast.expression.ArrayCreationExpression;
import org.forsook.parser.java.ast.expression.Dimension;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("15.10")
@ParseletDefinition(
        name = "forsook.java.arrayCreationExpression",
        emits = ArrayCreationExpression.class,
        needs = {
            PrimitiveType.class,
            ClassOrInterfaceType.class,
            Expression.class,
            ArrayInitializerExpression.class
        }
)
public class ArrayCreationExpressionParselet extends ExpressionParselet<ArrayCreationExpression> {

    @Override
    public ArrayCreationExpression parse(Parser parser) {
        //new
        if (!parser.peekPresentAndSkip("new")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type
        Type type = (Type) parser.first(PrimitiveType.class, ClassOrInterfaceType.class);
        if (type == null) {
            return null;
        }
        //dimensions
        List<Dimension> dimensions = new ArrayList<Dimension>();
        boolean nonExpressionDimFound = false;
        boolean expressionDimFound = false;
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //bracket
            if (!parser.peekPresentAndSkip('[')) {
                break;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //expression
            Expression expression = parser.next(Expression.class);
            if (expression == null) {
                nonExpressionDimFound = true;
            } else if (nonExpressionDimFound) {
                return null;
            } else {
                expressionDimFound = true;
                //spacing
                parseWhiteSpaceAndComments(parser);
            }
            //bracket
            if (!parser.peekPresentAndSkip(']')) {
                return null;
            }
            dimensions.add(new Dimension(expression));
        } while (true);
        if (dimensions.isEmpty()) {
            return null;
        }
        //initializer
        ArrayInitializerExpression initializer = null;
        if (expressionDimFound) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            initializer = parser.next(ArrayInitializerExpression.class); 
        }
        return new ArrayCreationExpression(type, dimensions, initializer);
    }
}
