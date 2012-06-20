package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.MarkerAnnotationExpression;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7.2")
@ParseletDefinition(
        name = "forsook.java.markerAnnotationExpression",
        emits = MarkerAnnotationExpression.class,
        needs = QualifiedName.class
)
public class MarkerAnnotationExpressionParselet extends AnnotationExpressionParselet<MarkerAnnotationExpression> {

    @Override
    public MarkerAnnotationExpression parse(Parser parser) {
        if (!parser.peekPresentAndSkip('@')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //name
        QualifiedName name = parser.next(QualifiedName.class);
        if (name == null) {
            return null;
        }
        return new MarkerAnnotationExpression(name);
    }

    
}
