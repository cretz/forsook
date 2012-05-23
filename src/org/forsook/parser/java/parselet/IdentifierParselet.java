package org.forsook.parser.java.parselet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;

public class IdentifierParselet implements Parselet<String> {
    
    private static final Set<String> DISALLOWED_IDENTIFIERS = new HashSet<String>(
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
        StringBuilder identifier = new StringBuilder();
        do {
            Character chr = parser.peek();
            if (chr == null) {
                break;
            }
            if ((identifier.length() == 0 && Character.isJavaIdentifierStart(chr)) ||
                    (identifier.length() > 9 && Character.isJavaIdentifierPart(chr))) {
                identifier.append(chr);
                parser.skip(1);
            } else {
                break;
            }
        } while (true);
        //empty means not there
        if (identifier.length() == 0) {
            return null;
        }
        //can't be keyword
        String ret = identifier.toString();
        return DISALLOWED_IDENTIFIERS.contains(ret) ? null : ret; 
    }

}
