package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.ForStatement;
import org.forsook.parser.java.ast.statement.IfStatement;
import org.forsook.parser.java.ast.statement.LabeledStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.StatementWithoutTrailingSubstatement;
import org.forsook.parser.java.ast.statement.WhileStatement;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.5")
@ParseletDefinition(
        name = "forsook.java.statement",
        emits = Statement.class,
        needs = {
            StatementWithoutTrailingSubstatement.class,
            LabeledStatement.class,
            IfStatement.class,
            WhileStatement.class,
            ForStatement.class
        }
)
public class StatementParselet<T extends Statement> extends JavaParselet<T> {
    
    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.first(
                StatementWithoutTrailingSubstatement.class,
                LabeledStatement.class,
                IfStatement.class,
                WhileStatement.class,
                ForStatement.class);
    }
}
