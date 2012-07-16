package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.AssertStatement;

@JlsReference("14.10")
@ParseletDefinition(
        name = "forsook.java.assertStatement",
        emits = AssertStatement.class,
        needs = Expression.class
)
public class AssertStatementParselet extends StatementParselet<AssertStatement> {

    @Override
    public AssertStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip("assert")) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ';')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //condition
        Expression condition = parser.next(Expression.class);
        if (condition == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //colon
        Expression message = null;
        if (parser.peekPresentAndSkip(':')) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //message
            message = parser.next(Expression.class);
            if (message == null) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new AssertStatement(condition, message);
    }
}
