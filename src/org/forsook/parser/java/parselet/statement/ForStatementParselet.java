package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.BasicForStatement;
import org.forsook.parser.java.ast.statement.EnhancedForStatement;
import org.forsook.parser.java.ast.statement.ForStatement;

@JlsReference("14.14")
@ParseletDefinition(
        name = "forsook.java.forStatement",
        emits = ForStatement.class,
        needs = { BasicForStatement.class, EnhancedForStatement.class }
)
public class ForStatementParselet<T extends ForStatement> extends StatementParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.first(BasicForStatement.class, EnhancedForStatement.class);
    }
}
