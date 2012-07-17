package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.InnerBlockStatement;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("14.2")
@ParseletDefinition(
        name = "forsook.java.blockStatement",
        emits = BlockStatement.class,
        needs = InnerBlockStatement.class
)
public class BlockStatementParselet extends StatementParselet<BlockStatement> {

    @Override
    public BlockStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), '}')) {
            return null;
        }
        //statements
        List<Statement> statements = new ArrayList<Statement>();
        //local class gets no javadoc IMO
        parseWhiteSpaceAndComments(parser);
        do {
            Statement stmt = (Statement) parser.next(InnerBlockStatement.class);
            if (stmt == null) {
                break;
            }
            statements.add(stmt);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (true);
        //brace
        if (!parser.peekPresentAndSkip('}')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new BlockStatement(statements);
    }

}
