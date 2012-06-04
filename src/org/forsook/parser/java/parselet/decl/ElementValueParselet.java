package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.ElementValue;
import org.forsook.parser.java.ast.decl.ElementValueArrayInitializerExpression;
import org.forsook.parser.java.ast.expression.ConditionalExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("9.7.1")
@ParseletDefinition(
        name = "forsook.java.elementValue",
        emits = ElementValue.class
)
public class ElementValueParselet extends JavaParselet<ElementValue> {

    @Override
    public ElementValue parse(Parser parser) {
        //conditional or annotation?
        Expression value = (Expression) parser.first(AnnotationExpression.class, 
                ConditionalExpression.class);
        if (value == null) {
            //should be an array initializer then
            if (!parser.peekPresentAndSkip('{')) {
                return null;
            }
            List<ElementValue> values = new ArrayList<ElementValue>();
            do {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //value
                ElementValue elementValue = parser.next(ElementValue.class);
                if (value != null) {
                    values.add(elementValue);
                }
                //spacing
                parseWhiteSpaceAndComments(parser);
            } while (parser.peekPresentAndSkip(','));
            if (!parser.peekPresentAndSkip('}')) {
                return null;
            }
            value = new ElementValueArrayInitializerExpression(values);
        }
        return new ElementValue(value);
    }

}
