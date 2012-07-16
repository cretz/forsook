package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.statement.SwitchBlockStatementGroup;
import org.forsook.parser.java.ast.statement.SwitchStatement;

@JlsReference("14.11")
@ParseletDefinition(
        name = "forsook.java.switchStatement",
        emits = SwitchStatement.class,
        needs = { Expression.class, SwitchBlockStatementGroup.class }
)
public class SwitchStatementParselet extends StatementParselet<SwitchStatement> {

    @Override
    public SwitchStatement parse(Parser parser) {
        //switch
        if (!parser.peekPresentAndSkip("switch")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //selector
        Expression selector = parser.next(Expression.class);
        if (selector == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //brace
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), '}')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //switch block statement groups
        List<SwitchBlockStatementGroup> entries = new ArrayList<SwitchBlockStatementGroup>();
        boolean defaultFound = false;
        boolean previousHadNoStatements = false;
        do {
            SwitchBlockStatementGroup entry = parser.next(SwitchBlockStatementGroup.class);
            if (entry == null) {
                break;
            }
            if (entry.isDefaultPresent()) {
                if (defaultFound) {
                    //two defaults
                    return null;
                }
                defaultFound = true;
            }
            if (entry.getStatements().isEmpty()) {
                if (previousHadNoStatements) {
                    //strange
                    return null;
                }
                previousHadNoStatements = true;
            }
            entries.add(entry);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (!parser.peekPresentAndSkip('}'));
        //pop lookahead
        parser.popLookAhead();
        return new SwitchStatement(selector, entries);
    }
}
