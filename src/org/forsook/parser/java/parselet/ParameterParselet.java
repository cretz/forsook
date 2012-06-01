package org.forsook.parser.java.parselet;

import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.Parameter;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.VariableDeclaratorId;

@JlsReference("8.4.1")
@ParseletDefinition(
        name = "forsook.java.parameter",
        emits = Parameter.class,
        needs = {
            AnnotationExpression.class,
            ReferenceType.class,
            VariableDeclaratorId.class
        }
)
public class ParameterParselet extends JavaParselet<Parameter> {

    @Override
    @SuppressWarnings("unchecked")
    public Parameter parse(Parser parser) {
        //annotations
        List<AnnotationExpression> annotations = 
                (List<AnnotationExpression>) parser.any(AnnotationExpression.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //final?
        int modifier = 0;
        if (parser.peekPresentAndSkip("final")) {
            modifier = java.lang.reflect.Modifier.FINAL;
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //type
        ReferenceType type = parser.next(ReferenceType.class);
        if (type == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //varargs?
        boolean varArgs = false;
        if (parser.peekPresentAndSkip("...")) {
            varArgs = true;
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //name
        VariableDeclaratorId name = parser.next(VariableDeclaratorId.class);
        if (name == null) {
            return null;
        }
        return new Parameter(new Modifier(modifier), annotations, type, varArgs, name);
    }

}
