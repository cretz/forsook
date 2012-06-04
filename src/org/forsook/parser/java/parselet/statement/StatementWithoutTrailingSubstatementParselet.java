package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.AssertStatement;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.BreakStatement;
import org.forsook.parser.java.ast.statement.ContinueStatement;
import org.forsook.parser.java.ast.statement.DoStatement;
import org.forsook.parser.java.ast.statement.EmptyStatement;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.ReturnStatement;
import org.forsook.parser.java.ast.statement.StatementWithoutTrailingSubstatement;
import org.forsook.parser.java.ast.statement.SwitchStatement;
import org.forsook.parser.java.ast.statement.SynchronizedStatement;
import org.forsook.parser.java.ast.statement.ThrowStatement;
import org.forsook.parser.java.ast.statement.TryStatement;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.5")
@ParseletDefinition(
        name = "forsook.java.statementWithoutTrailingSubstatement",
        emits = StatementWithoutTrailingSubstatement.class,
        needs = {
            BlockStatement.class,
            EmptyStatement.class,
            ExpressionStatement.class,
            AssertStatement.class,
            SwitchStatement.class,
            DoStatement.class,
            BreakStatement.class,
            ContinueStatement.class,
            ReturnStatement.class,
            SynchronizedStatement.class,
            ThrowStatement.class,
            TryStatement.class
        }
)
public class StatementWithoutTrailingSubstatementParselet 
        extends JavaParselet<StatementWithoutTrailingSubstatement> {

    @Override
    public StatementWithoutTrailingSubstatement parse(Parser parser) {
        return (StatementWithoutTrailingSubstatement) parser.first(
                BlockStatement.class,
                EmptyStatement.class,
                ExpressionStatement.class,
                AssertStatement.class,
                SwitchStatement.class,
                DoStatement.class,
                BreakStatement.class,
                ContinueStatement.class,
                ReturnStatement.class,
                SynchronizedStatement.class,
                ThrowStatement.class,
                TryStatement.class
            );
    }

}
