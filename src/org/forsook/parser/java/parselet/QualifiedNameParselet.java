package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.QualifiedName;

@JlsReference("6.5") //TODO check this
@ParseletDefinition(
        name = "forsook.java.qualifiedName",
        emits = QualifiedName.class
)
public class QualifiedNameParselet extends JavaParselet<QualifiedName> {

    @Override
    public QualifiedName parse(Parser parser) {
        //simply put, either an identifier char or a dot
        StringBuilder builder = new StringBuilder();
        do {
            if (builder.length() > 0) {
                builder.append('.');
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //identifier
            String identifier = nextWord(parser);
            if (identifier == null) {
                break;
            } else {
                builder.append(identifier);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip('.'));
        if (builder.length() == 0) {
            return null;
        } else {
            return new QualifiedName(builder.toString());
        }
    }

}
