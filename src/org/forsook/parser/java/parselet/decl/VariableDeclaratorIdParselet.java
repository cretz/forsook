package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("8.3")
@ParseletDefinition(
        name = "forsook.java.variableDeclaratorId",
        emits = VariableDeclaratorId.class
)
public class VariableDeclaratorIdParselet extends JavaParselet<VariableDeclaratorId> {

    @Override
    public VariableDeclaratorId parse(Parser parser) {
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //array count
        int arrayCount = 0;
        do {
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip('[')) {
                break;
            }
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip(']')) {
                return null;
            }
            arrayCount++;
        } while (true);
        return new VariableDeclaratorId(name, arrayCount);
    }

}
