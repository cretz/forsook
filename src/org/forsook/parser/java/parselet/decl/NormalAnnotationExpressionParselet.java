package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.decl.NormalAnnotationExpression;
import org.forsook.parser.java.ast.decl.NormalAnnotationExpression.ElementValuePair;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7.1")
@ParseletDefinition(
        name = "forsook.java.normalAnnotationExpression",
        emits = NormalAnnotationExpression.class
)
public class NormalAnnotationExpressionParselet extends AnnotationExpressionParselet<NormalAnnotationExpression> {

    @Override
    public NormalAnnotationExpression parse(Parser parser) {
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
        //element value pairs
        List<ElementValuePair> pairs = new ArrayList<ElementValuePair>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //name
            Identifier pairName = parser.next(Identifier.class);
            if (pairName == null) {
                break;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //equals
            if (!parser.peekPresentAndSkip('=')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //value
            Expression value = parseElementValue(parser);
            if (value == null) {
                return null;
            }
            pairs.add(new ElementValuePair(pairName, value));
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        return new NormalAnnotationExpression(name, pairs);
    }

    
}
