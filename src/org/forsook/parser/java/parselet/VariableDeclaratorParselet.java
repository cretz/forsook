package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ArrayInitializerExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.VariableDeclarator;
import org.forsook.parser.java.ast.VariableDeclaratorId;

@JlsReference("14.4")
@ParseletDefinition(
        name = "forsook.java.variableDeclarator",
        emits = VariableDeclarator.class,
        needs = { Identifier.class, ArrayInitializerExpression.class, Expression.class }
)
public class VariableDeclaratorParselet extends JavaParselet<VariableDeclarator> {

    @Override
    public VariableDeclarator parse(Parser parser) {
        //name
        Identifier name = parser.next(Identifier.class);
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
            init = parser.next(ArrayInitializerExpression.class);
            if (init == null) {
                //normal expression?
                init = parser.next(Expression.class);
                if (init == null) {
                    return null;
                }
            }
        }
        return new VariableDeclarator(new VariableDeclaratorId(name, arrayCount), init);
    }

}
