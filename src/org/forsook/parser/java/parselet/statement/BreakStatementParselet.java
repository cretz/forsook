package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.statement.BreakStatement;

@JlsReference("14.15")
@ParseletDefinition(
        name = "forsook.java.breakStatement",
        emits = BreakStatement.class,
        needs = Identifier.class
)
public class BreakStatementParselet extends StatementParselet<BreakStatement> {

    @Override
    public BreakStatement parse(Parser parser) {
        //break
        if (!parser.peekPresentAndSkip("break")) {
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
        return new BreakStatement(label);
    }
}
