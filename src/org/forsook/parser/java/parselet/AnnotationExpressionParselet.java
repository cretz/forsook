package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;

@JlsReference("9.7")
@ParseletDefinition(
        name = "forsook.java.annotationExpression",
        emits = AnnotationExpression.class
)
public class AnnotationExpressionParselet extends ExpressionParselet<AnnotationExpression> {

    @Override
    public AnnotationExpression parse(Parser parser) {
        // TODO Auto-generated method stub
        return null;
    }

}
