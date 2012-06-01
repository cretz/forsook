package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.BasicForStatement;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.Statement;
import org.forsook.parser.java.ast.VariableDeclarationExpression;

@JlsReference("14.14.1")
@ParseletDefinition(
        name = "forsook.java.basicForStatement",
        emits = BasicForStatement.class,
        needs = {
            VariableDeclarationExpression.class,
            Expression.class,
            Statement.class
        }
)
public class BasicForStatementParselet extends ForStatementParselet<BasicForStatement> {

    @Override
    public BasicForStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip("for")) {
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
        //init
        List<Expression> init = new ArrayList<Expression>();
        Expression expr = parser.next(VariableDeclarationExpression.class);
        if (expr != null) {
            //var expression
            init.add(expr);
        } else {
            //expression list?
            do {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //expression
                //TODO: limit to statement expressions
                expr = parser.next(Expression.class);
                if (expr == null) {
                    break;
                }
                init.add(expr);
                //spacing
                parseWhiteSpaceAndComments(parser);
            } while (parser.peekPresentAndSkip(','));
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression compare = parser.next(Expression.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //update
        List<Expression> update = new ArrayList<Expression>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //expression
            //TODO: limit to statement expressions
            expr = parser.next(Expression.class);
            if (expr == null) {
                break;
            }
            update.add(expr);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //body
        Statement body = parser.next(Statement.class);
        if (body == null) {
            return null;
        }
        return new BasicForStatement(init, compare, update, body);
    }
}
