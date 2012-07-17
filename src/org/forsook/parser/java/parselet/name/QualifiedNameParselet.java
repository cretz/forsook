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
        boolean dot = false;
        boolean endsWithDot = false;
        do {
            //dot?
            dot = parser.pushFirstDepthLookAhead(parser.getAstDepth(), '.');
            //spacing
            parseWhiteSpaceAndComments(parser);
            //identifier
            Identifier identifier = parser.next(Identifier.class);
            if (identifier == null) {
                break;
            }
            endsWithDot = false;
            identifiers.add(identifier);
            if (!dot) {
                break;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip('.')) {
                break;
            }
            endsWithDot = true;
            parser.popLookAhead();
        } while (true);
        if (identifiers.isEmpty()) {
            return null;
        } 
        if (endsWithDot) {
            //uh oh, backup
            parser.backupCursor();
        }
        if (dot) {
            //pop
            parser.popLookAhead();
        }
        return new QualifiedName(identifiers);
    }

}
