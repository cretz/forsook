package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression.AssignmentOperator;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.26")
@ParseletDefinition(
        name = "forsook.java.assignmentOperatorExpression",
        emits = AssignmentOperatorExpression.class,
        needs = {
            QualifiedName.class,
            FieldAccessExpression.class,
            ArrayAccessExpression.class,
            AssignmentExpression.class
        },
        recursiveMinimumSize = 2
)
public class AssignmentOperatorExpressionParselet
        extends ExpressionParselet<AssignmentOperatorExpression> {

    public AssignmentOperatorExpression parse(Parser parser) {
        //left
        Expression left = (Expression) parser.first(QualifiedName.class, 
                FieldAccessExpression.class, ArrayAccessExpression.class);
        if (left == null) {
            return null;
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
        //right
        Expression right = (Expression) parser.next(AssignmentExpression.class);
        if (right == null) {
            return null;
        }
        return new AssignmentOperatorExpression(left, operator, right);
    }
}
