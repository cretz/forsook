package org.forsook.parser.java.parselet;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.AssignmentExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.NameExpression;
import org.forsook.parser.java.ast.AssignmentExpression.AssignmentOperator;

public class AssignmentExpressionParselet extends ExpressionParselet<AssignmentExpression> {

    @Override
    public AssignmentExpression parse(Parser parser) {
        //array access?
        Expression leftHand = parser.next(ArrayAccessExpressionParselet.class);
        if (leftHand == null) {
            //must be qualified name
            String name = parser.next(QualifiedNameParselet.class);
            if (name == null) {
                return null;
            }
            leftHand = new NameExpression(name);
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
        Expression rightHand = parseExpression(parser);
        if (rightHand == null) {
            return null;
        }
        return new AssignmentExpression(leftHand, operator, rightHand);
    }

}
