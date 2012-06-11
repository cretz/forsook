package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.Parameter;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.parselet.JavaParselet;

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
    public Parameter parse(Parser parser) {
        //annotations
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        boolean finalPresent = false;
        do {
            parseWhiteSpaceAndComments(parser);
            Object object = parser.first(AnnotationExpression.class, Modifier.class);
            if (object == null) {
                break;
            } else if (object instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) object);
            } else {
                if (object != Modifier.FINAL) {
                    return null;
                }
                finalPresent = true;
            }
        } while (true);
        //type
        Type type = parser.next(Type.class);
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
        return new Parameter(finalPresent, annotations, type, varArgs, name);
    }

}
