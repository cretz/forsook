package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Statement;

@JlsReference("14.5")
@ParseletDefinition(
        name = "forsook.java.statement",
        emits = Statement.class
)
public class StatementParselet<T extends Statement> extends JavaParselet<T> {

    public static boolean isStatementNoShortIf(Statement statement) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public T parse(Parser parser) {
        
    }
}
