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
        //lookahead
        if (!parser.pushLookAhead('.')) {
            return null;
        }
        //scope
        Expression scope = (Expression) parser.next(PrimaryExpression.class);
        //class name
        QualifiedName className = null;
        boolean superPresent = false;
        Identifier field = null;
        if (scope == null) {
            //grab qualified name
            className = parser.next(QualifiedName.class);
            if (className != null) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (!parser.peekPresentAndSkip('.')) {
                    //so, no dot? well if the class name has more
                    //  than one identifier, it's ok
                    if (className.getIdentifiers().size() == 1) {
                        return null;
                    }
                    field = className.getIdentifiers().get(
                            className.getIdentifiers().size() - 1);
                    className = new QualifiedName(
                            className.getIdentifiers().subList(0, 
                                    className.getIdentifiers().size() - 1));
                } else {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                }
            }
            if (field == null) {
                //super
                superPresent = parser.peekPresentAndSkip("super");
                if (superPresent) {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                }
            }
        } else {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //dot (only required if field isn't already set)
        if (field == null && !parser.peekPresentAndSkip('.')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        if (field == null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //field
            field = parser.next(Identifier.class);
            if (field == null) {
                return null;
            }
        }
        return new FieldAccessExpression(scope, className, superPresent, field);
    }
}
