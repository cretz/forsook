package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.BasicForNoShortIfStatement;
import org.forsook.parser.java.ast.statement.BasicForStatement;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("14.14.1")
@ParseletDefinition(
        name = "forsook.java.basicForNoShortIfStatement",
        emits = BasicForNoShortIfStatement.class,
        needs = NoShortIfStatement.class
)
public class BasicForNoShortIfStatementParselet extends BasicForStatementParselet {

    @Override
    public BasicForNoShortIfStatement parse(Parser parser) {
        BasicForStatement stmt = super.parse(parser);
        if (stmt == null) {
            return null;
        }
        return new BasicForNoShortIfStatement(stmt.getInit(), 
                stmt.getCompare(), stmt.getUpdate(), stmt.getBody());
    }
    
    @Override
    protected Statement parseStatement(Parser parser) {
        return (Statement) parser.next(NoShortIfStatement.class);
    }
}
