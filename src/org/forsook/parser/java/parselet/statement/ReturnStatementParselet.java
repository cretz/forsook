package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.ReturnStatement;

@JlsReference("14.17")
@ParseletDefinition(
        name = "forsook.java.returnStatement",
        emits = ReturnStatement.class,
        needs = Expression.class
)
public class ReturnStatementParselet extends StatementParselet<ReturnStatement> {

    @Override
    public ReturnStatement parse(Parser parser) {
        //return
        if (!parser.peekPresentAndSkip("return")) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ';')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression?
        Expression expression = parser.next(Expression.class);
        if (expression != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new ReturnStatement(expression);
    }
}
