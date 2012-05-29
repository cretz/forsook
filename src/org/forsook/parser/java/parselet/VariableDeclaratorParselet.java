package org.forsook.parser.java.parselet;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.VariableDeclarator;
import org.forsook.parser.java.ast.VariableDeclaratorId;

public class VariableDeclaratorParselet extends JavaParselet<VariableDeclarator> {

    @Override
    public VariableDeclarator parse(Parser parser) {
        //name
        String name = parser.next(IdentifierParselet.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //array count
        int arrayCount = 0;
        do {
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip('[')) {
                break;
            }
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip(']')) {
                return null;
            }
            arrayCount++;
        } while (true);
        //initializer?
        Expression init = null;
        if (parser.peekPresentAndSkip('=')) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //array initializer?
            init = parser.next(ArrayInitializerExpressionParselet.class);
            if (init == null) {
                //normal expression?
                init = ExpressionParselet.parseExpression(parser);
                if (init == null) {
                    return null;
                }
            }
        }
        return new VariableDeclarator(new VariableDeclaratorId(name, arrayCount), init);
    }

}
