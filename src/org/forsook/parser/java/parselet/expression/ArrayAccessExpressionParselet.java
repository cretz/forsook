package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.ArrayCreationExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PrimaryNoNewArrayExpression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.13")
@ParseletDefinition(
        name = "forsook.java.arrayAccessExpression",
        emits = ArrayAccessExpression.class,
        needs = { 
            PrimaryNoNewArrayExpression.class,
            QualifiedName.class, 
            Expression.class 
        },
        recursiveMinimumSize = 2
)
public class ArrayAccessExpressionParselet extends ExpressionParselet<ArrayAccessExpression> {

    @Override
    public ArrayAccessExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead('[')) {
            return null;
        }
        //name
        Expression name = (Expression) parser.next(PrimaryNoNewArrayExpression.class);
        if (name == null || name instanceof ArrayCreationExpression) {
            //ok, just string
            name = parser.next(QualifiedName.class);
            if (name == null) {
                return null;
            }
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //bracket
        if (!parser.peekPresentAndSkip('[')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushLookAhead(']')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression index = parser.next(Expression.class);
        if (index == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //bracket
        if (!parser.peekPresentAndSkip(']')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //need a check here to make sure it's not a method or field
        parseWhiteSpaceAndComments(parser);
        return new ArrayAccessExpression(name, index);
    }

}
