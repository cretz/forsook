package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.statement.InnerBlockStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.SwitchBlockStatementGroup;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.11")
@ParseletDefinition(
        name = "forsook.java.switchBlockStatementGroup",
        emits = SwitchBlockStatementGroup.class,
        needs = {
            Identifier.class,
            Expression.class,
            InnerBlockStatement.class
        }
)
public class SwitchBlockStatementGroupParselet extends JavaParselet<SwitchBlockStatementGroup> {

    @Override
    public SwitchBlockStatementGroup parse(Parser parser) {
        //loop through the labels
        List<Expression> labels = new ArrayList<Expression>();
        boolean defaultPresent = false;
        do {
            Expression label = null;
            //default?
            if (parser.peekPresentAndSkip("default")) {
                defaultPresent = true;
            } else if (parser.peekPresentAndSkip("case")) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //identifier?
                label = parser.next(Expression.class);
                if (label == null) {
                    return null;
                }
            } else {
                break;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //colon
            if (!parser.peekPresentAndSkip(':')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            if (label != null) {
                labels.add(label);
            }
        } while (true);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //statements
        List<Statement> statements = new ArrayList<Statement>();
        do {
            Statement stmt = (Statement) parser.next(InnerBlockStatement.class);
            if (stmt == null) {
                break;
            }
            statements.add(stmt);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (true);
        return new SwitchBlockStatementGroup(labels, defaultPresent, statements);
    }

}
