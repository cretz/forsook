package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.CatchClause;
import org.forsook.parser.java.ast.statement.Resource;
import org.forsook.parser.java.ast.statement.TryStatement;
import org.forsook.parser.java.ast.statement.TryWithResourcesStatement;

@JlsReference("14.20.3")
@ParseletDefinition(
        name = "forsook.java.tryWithResourcesStatement",
        emits = TryStatement.class,
        needs = { BlockStatement.class, CatchClause.class, Resource.class }
)
public class TryWithResourcesStatementParselet extends TryStatementParselet {

    @Override
    public TryWithResourcesStatement parse(Parser parser) {
        //try
        if (!parser.peekPresentAndSkip("try")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushLookAhead(')')) {
            return null;
        }
        //resources
        List<Resource> resources = new ArrayList<Resource>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //resource
            Resource resource = parser.next(Resource.class);
            if (resource == null) {
                return null;
            }
            resources.add(resource);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(';'));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
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
        return new TryWithResourcesStatement(tryBlock, catchClauses, finallyBlock, resources);
    }
}
