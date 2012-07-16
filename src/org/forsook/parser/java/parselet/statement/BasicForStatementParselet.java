package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.BasicForStatement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.StatementExpression;

@JlsReference("14.14.1")
@ParseletDefinition(
        name = "forsook.java.basicForStatement",
        emits = BasicForStatement.class,
        needs = {
            LocalVariableDeclarationExpression.class,
            Expression.class,
            Statement.class,
            StatementExpression.class
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
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ';')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //init
        List<Expression> init = new ArrayList<Expression>();
        Expression expr = parser.next(LocalVariableDeclarationExpression.class);
        if (expr != null) {
            //var expression
            init.add(expr);
        } else {
            //expression list?
            do {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //expression
                expr = (Expression) parser.next(StatementExpression.class);
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
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ';')) {
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
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ')')) {
            return null;
        }
        //update
        List<Expression> update = new ArrayList<Expression>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //expression
            expr = (Expression) parser.next(StatementExpression.class);
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
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //body
        Statement body = parseStatement(parser);
        if (body == null) {
            return null;
        }
        return new BasicForStatement(init, compare, update, body);
    }
    
    protected Statement parseStatement(Parser parser) {
        //abstracted for no-short-if
        return parser.next(Statement.class);
    }
}
