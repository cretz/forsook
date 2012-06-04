package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.decl.ElementValue;
import org.forsook.parser.java.ast.decl.SingleElementAnnotationExpression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7.3")
@ParseletDefinition(
        name = "forsook.java.singleElementAnnotationExpression",
        emits = SingleElementAnnotationExpression.class,
        needs = { QualifiedName.class, Expression.class }
)
public class SingleElementAnnotationParselet 
        extends AnnotationExpressionParselet<SingleElementAnnotationExpression> {

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
        ElementValue value = parser.next(ElementValue.class);
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
