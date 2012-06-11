package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference({ "8.1.1", "8.3.1", "8.4.1", "8.4.3", "8.8.3", "9.1.1", "9.3", "9.4" })
@ParseletDefinition(
        name = "forsook.java.modifier",
        emits = Modifier.class
)
public class ModifierParselet extends JavaParselet<Modifier> {
    
    @Override
    public Modifier parse(Parser parser) {
        //must be a character obviously
        if (parser.isPeekEndOfInput() || !Character.isLetter(parser.peek())) {
            //TODO: check whether this speeds things up or not
            return null;
        }
        //get which one
        for (Modifier modifier : Modifier.values()) {
            if (parser.peekPresentAndSkip(modifier.getLowerCase())) {
                return modifier;
            }
        }
        return null;
    }
}
