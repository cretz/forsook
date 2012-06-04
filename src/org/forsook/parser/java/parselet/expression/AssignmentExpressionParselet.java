package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.AssignmentExpression.AssignmentOperator;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.26")
@ParseletDefinition(
        name = "forsook.java.assignmentExpression",
        emits = AssignmentExpression.class,
        needs = { 
            ArrayAccessExpression.class, 
            QualifiedName.class,
            Expression.class
        }
)
public class AssignmentExpressionParselet extends ExpressionParselet<AssignmentExpression> {

    @Override
    public AssignmentExpression parse(Parser parser) {
        //array access?
        Expression leftHand = parser.next(ArrayAccessExpression.class);
        if (leftHand == null) {
            //must be qualified name
            leftHand = parser.next(QualifiedName.class);
            if (leftHand == null) {
                return null;
            }
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //operator
        AssignmentOperator operator = null;
        for (AssignmentOperator possible : AssignmentOperator.values()) {
            if (parser.peekPresentAndSkip(possible.toString())) {
                operator = possible;
                break;
            }
        }
        if (operator == null) {
            return null;
        }
        //right hand is normal expression
        Expression rightHand = parser.next(Expression.class);
        if (rightHand == null) {
            return null;
        }
        return new AssignmentExpression(leftHand, operator, rightHand);
    }

}
