package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.MarkerAnnotationExpression;
import org.forsook.parser.java.ast.decl.NormalAnnotationExpression;
import org.forsook.parser.java.ast.decl.SingleElementAnnotationExpression;
import org.forsook.parser.java.parselet.expression.ExpressionParselet;

@JlsReference("9.7")
@ParseletDefinition(
        name = "forsook.java.annotationExpression",
        emits = AnnotationExpression.class,
        needs = {
            SingleElementAnnotationExpression.class,
            NormalAnnotationExpression.class,
            MarkerAnnotationExpression.class
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
}
