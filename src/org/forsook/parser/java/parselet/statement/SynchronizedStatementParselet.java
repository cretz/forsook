package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.SynchronizedStatement;

@JlsReference("14.19")
@ParseletDefinition(
        name = "forsook.java.synchronizedStatement",
        emits = SynchronizedStatement.class,
        needs = { Expression.class, BlockStatement.class }
)
public class SynchronizedStatementParselet extends StatementParselet<SynchronizedStatement> {

    @Override
    public SynchronizedStatement parse(Parser parser) {
        //synchronized
        if (!parser.peekPresentAndSkip("synchronized")) {
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
        //expression
        Expression expression = parser.next(Expression.class);
        if (expression == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //block
        BlockStatement block = parser.next(BlockStatement.class);
        if (block == null) {
            return null;
        }
        return new SynchronizedStatement(expression, block);
    }
}
