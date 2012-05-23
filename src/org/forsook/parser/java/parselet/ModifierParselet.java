package org.forsook.parser.java.parselet;

import java.lang.reflect.Modifier;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;

public class ModifierParselet implements Parselet<Integer> {
    
    @Override
    public Integer parse(Parser parser) {
        //must be a character obviously
        if (parser.isPeekEndOfInput() || !Character.isLetter(parser.peek())) {
            //TODO: check whether this speeds things up or not
            return null;
        }
        //get which one
        if (parser.peekPresentAndSkip("public")) {
            return Modifier.PUBLIC;
        } else if (parser.peekPresentAndSkip("static")) {
            return Modifier.STATIC;
        } else if (parser.peekPresentAndSkip("protected")) {
            return Modifier.PROTECTED;
        } else if (parser.peekPresentAndSkip("private")) {
            return Modifier.PRIVATE;
        } else if (parser.peekPresentAndSkip("final")) {
            return Modifier.FINAL;
        } else if (parser.peekPresentAndSkip("abstract")) {
            return Modifier.ABSTRACT;
        } else if (parser.peekPresentAndSkip("synchronized")) {
            return Modifier.SYNCHRONIZED;
        } else if (parser.peekPresentAndSkip("native")) {
            return Modifier.NATIVE;
        } else if (parser.peekPresentAndSkip("transient")) {
            return Modifier.TRANSIENT;
        } else if (parser.peekPresentAndSkip("volatile")) {
            return Modifier.VOLATILE;
        } else if (parser.peekPresentAndSkip("strictfp")) {
            return Modifier.STRICT;
        } else {
            return null;
        }
    }
}
