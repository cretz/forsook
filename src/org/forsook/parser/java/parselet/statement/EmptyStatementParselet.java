package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.statement.EmptyStatement;

@JlsReference("14.6")
@ParseletDefinition(
        name = "forsook.java.emptyStatement",
        emits = EmptyStatement.class
)
public class EmptyStatementParselet extends StatementParselet<EmptyStatement> {

    @Override
    public EmptyStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new EmptyStatement();
    }
}
