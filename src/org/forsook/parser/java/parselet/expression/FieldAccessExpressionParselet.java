package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.11")
@ParseletDefinition(
        name = "forsook.java.fieldAccessExpression",
        emits = FieldAccessExpression.class
)
public class FieldAccessExpressionParselet extends ExpressionParselet<FieldAccessExpression> {

    @Override
    public FieldAccessExpression parse(Parser parser) {
        //just grab a simple qualified name first
        QualifiedName superName = parser.next(QualifiedName.class);
        Identifier fieldName = null;
        if (superName != null) {
            int lastDot = superName.getName().lastIndexOf('.');
            if (superName.getName().regionMatches(lastDot - 6, "super.", 0, 6)) {
                //TODO: fix me
            }
        }
                
    }
}
