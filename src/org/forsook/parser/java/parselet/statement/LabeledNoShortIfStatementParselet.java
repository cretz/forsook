package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.LabeledNoShortIfStatement;
import org.forsook.parser.java.ast.statement.LabeledStatement;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.Statement;

@JlsReference("14.7")
@ParseletDefinition(
        name = "forsook.java.labeledNoShortIfStatement",
        emits = LabeledNoShortIfStatement.class,
        needs = NoShortIfStatement.class
)
public class LabeledNoShortIfStatementParselet extends LabeledStatementParselet {

    @Override
    public LabeledNoShortIfStatement parse(Parser parser) {
        LabeledStatement stmt = super.parse(parser);
        if (stmt == null) {
            return null;
        }
        return new LabeledNoShortIfStatement(stmt.getIdentifier(), stmt.getStatement());
    }
    
    @Override
    protected Statement parseStatement(Parser parser) {
        return (Statement) parser.next(NoShortIfStatement.class);
    }
}
