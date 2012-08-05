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
        //can't call myself directly
        for (int i = parser.getParseletStack().size() - 2; i >= 0; i--) {
            if (parser.getParseletStack().get(i).getParselet().getClass() ==
                    ArrayAccessExpressionParselet.class) {
                return null;
            } else if (parser.getParseletStack().get(i).getParselet().getClass() != 
                    PrimaryExpressionParselet.class &&
                    parser.getParseletStack().get(i).getParselet().getClass() !=
                    PrimaryNoNewArrayExpressionParselet.class) {
                break;
            }
        }
        //lookahead
        if (!parser.pushLastLookAheadNoDeeperThan(parser.peekAstDepth(), ']')) {
            return null;
        }
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
        //pop lookahead
        parser.popLookAhead();
        //bracket
        if (!parser.peekPresentAndSkip('[')) {
            //could have gotten an array access
            if (name instanceof ArrayAccessExpression) {
                return (ArrayAccessExpression) name;
            }
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
        ArrayAccessExpression expr = new ArrayAccessExpression(name, index);
        do {
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip('[')) {
                break;
            }
            if (!parser.pushLastDepthLookAhead(parser.getAstDepth(), ']')) {
                return null;
            }
            parseWhiteSpaceAndComments(parser);
            index = parser.next(Expression.class);
            if (index != null) {
                parseWhiteSpaceAndComments(parser);
            }
            if (!parser.peekPresentAndSkip(']')) {
                return null;
            }
            parser.popLookAhead();
            expr = new ArrayAccessExpression(expr, index);
        } while (true);
        return expr;
    }

}
