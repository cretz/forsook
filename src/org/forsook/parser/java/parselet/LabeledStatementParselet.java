package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.LabeledStatement;
import org.forsook.parser.java.ast.Statement;

@JlsReference("14.7")
@ParseletDefinition(
        name = "forsook.java.labeledStatement",
        emits = LabeledStatement.class,
        needs = { Identifier.class, Statement.class }
)
public class LabeledStatementParselet extends StatementParselet<LabeledStatement> {

    @Override
    public LabeledStatement parse(Parser parser) {
        Identifier identifier = parser.next(Identifier.class);
        if (identifier == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //colon
        if (!parser.peekPresentAndSkip(':')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //statement
        Statement statement = parser.next(Statement.class);
        if (statement == null) {
            return null;
        }
        return new LabeledStatement(identifier, statement);
    }
}
