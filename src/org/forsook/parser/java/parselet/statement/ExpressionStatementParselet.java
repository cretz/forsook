package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AssignmentExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.ExpressionStatement;

@JlsReference("14.8")
@ParseletDefinition(
        name = "forsook.java.expressionStatement",
        emits = ExpressionStatement.class
)
public class ExpressionStatementParselet extends StatementParselet<ExpressionStatement> {

    @Override
    public ExpressionStatement parse(Parser parser) {
        Expression expr = parser.first(
                AssignmentExpression.class,
                );
        //TODO: prefix, postfix statement
    }
}
