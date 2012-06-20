package org.forsook.parser.java.parselet.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.NonWildTypeArguments;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.12")
@ParseletDefinition(
        name = "forsook.java.methodInvocationExpressionParselet",
        emits = MethodInvocationExpression.class,
        needs = {
            PrimaryExpression.class,
            QualifiedName.class,
            NonWildTypeArguments.class,
            Identifier.class,
            Expression.class
        },
        recursiveMinimumSize = 2
)
public class MethodInvocationExpressionParselet
        extends ExpressionParselet<MethodInvocationExpression> {

    @Override
    public MethodInvocationExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead('(')) {
            return null;
        }
        //scope
        Expression scope = (Expression) parser.next(PrimaryExpression.class);
        QualifiedName className = null;
        boolean superPresent = false;
        QualifiedName methodName = null;
        if (scope == null) {
            //class name
            className = parser.next(QualifiedName.class);
            if (className != null) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (parser.peekPresentAndSkip('.')) {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                } else  {
                    //method name
                    methodName = className;
                    className = null;
                }
            }
            //super
            superPresent = parser.peekPresentAndSkip("super");
            if (superPresent) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (!parser.peekPresentAndSkip('.')) {
                    return null;
                }
                //spacing
                parseWhiteSpaceAndComments(parser);
            }
        } else {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //type arguments
        NonWildTypeArguments typeArguments = null;
        if (methodName == null) {
            typeArguments = parser.next(NonWildTypeArguments.class);
            if (!superPresent && typeArguments == null) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //method name
            Identifier methodIdentifier = parser.next(Identifier.class);
            if (methodIdentifier == null) {
                return null;
            }
            //needs to be mutable list
            methodName = new QualifiedName(
                    new ArrayList<Identifier>(Arrays.asList(methodIdentifier)));
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushLookAhead(')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //arguments
        List<Expression> arguments = new ArrayList<Expression>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //argument
            Expression argument = parser.next(Expression.class);
            if (argument == null) {
                if (arguments.isEmpty()) {
                    break;
                } else  {
                    return null;
                }
            }
            arguments.add(argument);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new MethodInvocationExpression(scope, className, 
                superPresent, typeArguments, methodName, arguments);
    }
}
