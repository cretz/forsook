package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.CatchClause;
import org.forsook.parser.java.ast.statement.TryStatement;

@JlsReference("14.20")
@ParseletDefinition(
        name = "forsook.java.tryStatement",
        emits = TryStatement.class,
        needs = { BlockStatement.class, CatchClause.class }
)
public class TryStatementParselet extends StatementParselet<TryStatement> {

    @Override
    public TryStatement parse(Parser parser) {
        //try
        if (!parser.peekPresentAndSkip("try")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //try block
        BlockStatement tryBlock = parser.next(BlockStatement.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //catch clauses
        List<CatchClause> catchClauses = new ArrayList<CatchClause>();
        do {
            CatchClause clause = parser.next(CatchClause.class);
            parseWhiteSpaceAndComments(parser);
            if (clause == null) {
                break;
            }
            catchClauses.add(clause);
        } while (true);
        //finally
        BlockStatement finallyBlock = null;
        if (parser.peekPresentAndSkip("finally")) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //finally block
            finallyBlock = parser.next(BlockStatement.class);
        }
        return new TryStatement(tryBlock, catchClauses, finallyBlock);
    }
}
