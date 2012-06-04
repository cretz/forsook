package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.statement.ContinueStatement;

@JlsReference("14.16")
@ParseletDefinition(
        name = "forsook.java.continueStatement",
        emits = ContinueStatement.class,
        needs = Identifier.class
)
public class ContinueStatementParselet extends StatementParselet<ContinueStatement> {

    @Override
    public ContinueStatement parse(Parser parser) {
        //continue
        if (!parser.peekPresentAndSkip("continue")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //label?
        Identifier label = parser.next(Identifier.class);
        if (label != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new ContinueStatement(label);
    }
}
