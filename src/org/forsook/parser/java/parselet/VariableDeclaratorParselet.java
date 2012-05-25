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
            Expression expr = parser.next(ArrayInitializerExpressionParselet.class);
            if (expr == null) {
                
            }
        }
        return new VariableDeclarator(new VariableDeclaratorId(name, arrayCount), init);
    }

}
