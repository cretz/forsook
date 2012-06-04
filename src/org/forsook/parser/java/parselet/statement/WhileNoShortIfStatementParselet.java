package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.WhileNoShortIfStatement;
import org.forsook.parser.java.ast.statement.WhileStatement;

@JlsReference("14.12")
@ParseletDefinition(
        name = "forsook.java.whileNoShortIfStatement",
        emits = WhileNoShortIfStatement.class,
        needs = NoShortIfStatement.class
)
public class WhileNoShortIfStatementParselet extends WhileStatementParselet {

    @Override
    public WhileNoShortIfStatement parse(Parser parser) {
        WhileStatement stmt = super.parse(parser);
        if (stmt == null) {
            return null;
        }
        return new WhileNoShortIfStatement(stmt.getCondition(), stmt.getBody());
    }
    
    @Override
    protected Statement parseStatement(Parser parser) {
        return (Statement) parser.next(NoShortIfStatement.class);
    }
}
