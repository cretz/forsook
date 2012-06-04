package org.forsook.parser.java.parselet.lexical;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("3.8")
@ParseletDefinition(
        name = "forsook.java.identifier",
        emits = Identifier.class
)
public class IdentifierParselet extends JavaParselet<Identifier> {
    
    static final Set<String> DISALLOWED_IDENTIFIERS = new HashSet<String>(
            Arrays.asList("abstract", "continue", "for", "new", "switch",
                    "assert", "default", "if", "package", "synchronized",
                    "boolean", "do", "goto", "private", "this", "break",
                    "double", "implements", "protected", "throw", "byte",
                    "else", "import", "public", "throws", "case", "enum",
                    "instanceof", "return", "transient", "catch", "extends",
                    "int", "short", "try", "char", "final", "interface",
                    "static", "void", "class", "finally", "long", "strictfp",
                    "volatile", "const", "float", "native", "super", "while",
                    "true", "false", "null"));

    @Override
    public Identifier parse(Parser parser) {
        String ret = nextWord(parser);
        if (ret == null || DISALLOWED_IDENTIFIERS.contains(ret)) {
            return null;
        }
        return new Identifier(ret); 
    }

}
