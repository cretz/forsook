package org.forsook.parser.java.parselet.name;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("6.5")
@ParseletDefinition(
        name = "forsook.java.qualifiedName",
        emits = QualifiedName.class,
        needs = Identifier.class
)
public class QualifiedNameParselet extends JavaParselet<QualifiedName> {

    @Override
    public QualifiedName parse(Parser parser) {
        List<Identifier> identifiers = new ArrayList<Identifier>();
        boolean endsWithDot = false;
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //identifier
            Identifier identifier = parser.next(Identifier.class);
            if (identifier == null) {
                endsWithDot = true;
                break;
            } else {
                identifiers.add(identifier);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip('.'));
        if (identifiers.isEmpty()) {
            return null;
        } else {
            //take one off the cursor (scary)
            if (endsWithDot) {
                parser.backupCursor();
            }
            return new QualifiedName(identifiers);
        }
    }

}
