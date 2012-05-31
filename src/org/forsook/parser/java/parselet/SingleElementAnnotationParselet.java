package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.QualifiedName;
import org.forsook.parser.java.ast.SingleElementAnnotationExpression;

@JlsReference("9.7.3")
@ParseletDefinition(
        name = "forsook.java.singleElementAnnotationExpression",
        emits = SingleElementAnnotationExpression.class,
        needs = { QualifiedName.class, Expression.class }
)
public class SingleElementAnnotationParselet extends AnnotationExpressionParselet<SingleElementAnnotationExpression> {

    @Override
    public SingleElementAnnotationExpression parse(Parser parser) {
        if (!parser.peekPresentAndSkip('@')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //name
        QualifiedName name = parser.next(QualifiedName.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //value
        Expression value = parseElementValue(parser);
        if (value == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        return new SingleElementAnnotationExpression(name, value);
    }

    
}
