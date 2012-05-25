package org.forsook.parser.java.parselet;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.ArrayAccessExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.NameExpression;

public class ArrayAccessExpressionParselet extends ExpressionParselet<ArrayAccessExpression> {

    @Override
    public ArrayAccessExpression parse(Parser parser) {
        //try primary no new array
        Expression name = parser.next(PrimaryNoNewArrayExpressionParselet.class);
        if (name == null) {
            //ok, just string
            String string = parser.next(QualifiedNameParselet.class);
            if (string == null) {
                return null;
            }
            name = new NameExpression(string);
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //bracket
        if (!parser.peekPresentAndSkip('[')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression index = parseExpression(parser);
        if (index == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //bracket
        if (!parser.peekPresentAndSkip(']')) {
            return null;
        }
        return new ArrayAccessExpression(name, index);
    }

}
