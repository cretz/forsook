package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ConditionalExpression;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.MarkerAnnotationExpression;
import org.forsook.parser.java.ast.decl.NormalAnnotationExpression;
import org.forsook.parser.java.ast.decl.SingleElementAnnotationExpression;
import org.forsook.parser.java.parselet.ExpressionParselet;
import org.forsook.parser.java.parselet.JavaParselet;

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

    public static Expression parseElementValue(JavaParselet<? extends JavaModel>, Parser parser) {
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
    
    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.first(SingleElementAnnotationExpression.class, 
                NormalAnnotationExpression.class, MarkerAnnotationExpression.class);
    }
}
