package org.forsook.parser.java.parselet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.forsook.parser.Parser;

public class IdentifierParselet extends JavaParselet<String> {
    
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
    public String parse(Parser parser) {
        String ret = nextWord(parser);
        return DISALLOWED_IDENTIFIERS.contains(ret) ? null : ret; 
    }

}
