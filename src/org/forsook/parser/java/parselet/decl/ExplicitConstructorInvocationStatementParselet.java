package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.ExplicitConstructorInvocationStatement;
import org.forsook.parser.java.ast.decl.NonWildTypeArguments;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.parselet.statement.StatementParselet;

@JlsReference("8.8.7.1")
@ParseletDefinition(
        name = "forsook.java.explicitConstructorInvocationStatement",
        emits = ExplicitConstructorInvocationStatement.class,
        needs = {
            PrimaryExpression.class,
            NonWildTypeArguments.class,
            Expression.class
        }
)
public class ExplicitConstructorInvocationStatementParselet
        extends StatementParselet<ExplicitConstructorInvocationStatement> {

    @Override
    public ExplicitConstructorInvocationStatement parse(Parser parser) {
        //scope
        Expression scope = (Expression) parser.next(PrimaryExpression.class);
        if (scope != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //dot
            if (!parser.peekPresentAndSkip('.')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //type arguments
        NonWildTypeArguments typeArguments = parser.next(NonWildTypeArguments.class);
        if (typeArguments != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //this
        boolean thisPresent = parser.peekPresentAndSkip("this");
        if (!thisPresent && !parser.peekPresentAndSkip("super")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
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
        //spacing
        parseWhiteSpaceAndComments(parser);
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new ExplicitConstructorInvocationStatement(scope, 
                typeArguments, thisPresent, arguments);
    }
}
