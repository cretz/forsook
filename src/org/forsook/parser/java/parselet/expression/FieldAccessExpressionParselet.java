package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.11")
@ParseletDefinition(
        name = "forsook.java.fieldAccessExpression",
        emits = FieldAccessExpression.class,
        needs = {
            PrimaryExpression.class,
            QualifiedName.class,
            Identifier.class
        },
        recursiveMinimumSize = 2
)
public class FieldAccessExpressionParselet extends ExpressionParselet<FieldAccessExpression> {

    @Override
    public FieldAccessExpression parse(Parser parser) {
        //scope
        Expression scope = (Expression) parser.next(PrimaryExpression.class);
        //class name
        QualifiedName className = null;
        boolean superPresent = false;
        if (scope == null) {
            className = parser.next(QualifiedName.class);
            if (className != null) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (!parser.peekPresentAndSkip('.')) {
                    return null;
                }
                //spacing
                parseWhiteSpaceAndComments(parser);
            }
            //super
            superPresent = parser.peekPresentAndSkip("super");
            if (superPresent) {
                //spacing
                parseWhiteSpaceAndComments(parser);
            }
        } else {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //dot
        if (!parser.peekPresentAndSkip('.')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //field
        Identifier field = parser.next(Identifier.class);
        if (field == null) {
            return null;
        }
        return new FieldAccessExpression(scope, className, superPresent, field);
    }
}
