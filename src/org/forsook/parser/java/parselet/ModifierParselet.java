package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;

@JlsReference("6.6") //TODO: add the rest
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
        if (parser.peekPresentAndSkip("public")) {
            return new Modifier(java.lang.reflect.Modifier.PUBLIC);
        } else if (parser.peekPresentAndSkip("static")) {
            return new Modifier(java.lang.reflect.Modifier.STATIC);
        } else if (parser.peekPresentAndSkip("protected")) {
            return new Modifier(java.lang.reflect.Modifier.PROTECTED);
        } else if (parser.peekPresentAndSkip("private")) {
            return new Modifier(java.lang.reflect.Modifier.PRIVATE);
        } else if (parser.peekPresentAndSkip("final")) {
            return new Modifier(java.lang.reflect.Modifier.FINAL);
        } else if (parser.peekPresentAndSkip("abstract")) {
            return new Modifier(java.lang.reflect.Modifier.ABSTRACT);
        } else if (parser.peekPresentAndSkip("synchronized")) {
            return new Modifier(java.lang.reflect.Modifier.SYNCHRONIZED);
        } else if (parser.peekPresentAndSkip("native")) {
            return new Modifier(java.lang.reflect.Modifier.NATIVE);
        } else if (parser.peekPresentAndSkip("transient")) {
            return new Modifier(java.lang.reflect.Modifier.TRANSIENT);
        } else if (parser.peekPresentAndSkip("volatile")) {
            return new Modifier(java.lang.reflect.Modifier.VOLATILE);
        } else if (parser.peekPresentAndSkip("strictfp")) {
            return new Modifier(java.lang.reflect.Modifier.STRICT);
        } else {
            return null;
        }
    }
}
