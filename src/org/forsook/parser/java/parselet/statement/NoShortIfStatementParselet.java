package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.BasicForNoShortIfStatement;
import org.forsook.parser.java.ast.statement.IfNoShortIfStatement;
import org.forsook.parser.java.ast.statement.LabeledNoShortIfStatement;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.StatementWithoutTrailingSubstatement;
import org.forsook.parser.java.ast.statement.WhileNoShortIfStatement;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.5")
@ParseletDefinition(
        name = "forsook.java.noShortIfStatement",
        emits = NoShortIfStatement.class,
        needs = {
            StatementWithoutTrailingSubstatement.class,
            LabeledNoShortIfStatement.class,
            IfNoShortIfStatement.class,
            WhileNoShortIfStatement.class,
            BasicForNoShortIfStatement.class
        }
)
public class NoShortIfStatementParselet extends JavaParselet<NoShortIfStatement> {

    @Override
    public NoShortIfStatement parse(Parser parser) {
        return (NoShortIfStatement) parser.first(
                StatementWithoutTrailingSubstatement.class,
                LabeledNoShortIfStatement.class,
                IfNoShortIfStatement.class,
                WhileNoShortIfStatement.class,
                BasicForNoShortIfStatement.class
            );
    }

}
