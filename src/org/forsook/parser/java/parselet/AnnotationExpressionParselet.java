package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.ConditionalExpression;
import org.forsook.parser.java.ast.ElementValueArrayInitializerExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.MarkerAnnotationExpression;
import org.forsook.parser.java.ast.NormalAnnotationExpression;
import org.forsook.parser.java.ast.SingleElementAnnotationExpression;

@JlsReference("9.7")
@ParseletDefinition(
        name = "forsook.java.annotationExpression",
        emits = AnnotationExpression.class,
        needs = {
            AnnotationExpression.class,
            SingleElementAnnotationExpression.class,
            NormalAnnotationExpression.class,
            MarkerAnnotationExpression.class,
            ConditionalExpression.class
        }
)
public class AnnotationExpressionParselet<T extends AnnotationExpression> 
        extends ExpressionParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.first(SingleElementAnnotationExpression.class, 
                NormalAnnotationExpression.class, MarkerAnnotationExpression.class);
    }

    protected Expression parseElementValue(Parser parser) {
        //conditional or annotation?
        Expression value = (Expression) parser.first(AnnotationExpression.class, 
                ConditionalExpression.class);
        if (value != null) {
            return value;
        }
        //should be an array initializer then
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        List<Expression> values = new ArrayList<Expression>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //value
            value = parseElementValue(parser);
            if (value != null) {
                values.add(value);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        if (!parser.peekPresentAndSkip('}')) {
            return null;
        }
        return new ElementValueArrayInitializerExpression(values);
    }
}
