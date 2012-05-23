package org.forsook.parser.java.parselet;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.forsook.parser.java.ast.BlockComment;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.LineComment;
import org.forsook.parser.java.ast.PackageDeclaration;
import org.junit.Test;

public class SimpleParseletTest extends ParseletTestBase {

    @Test
    public void testWhiteSpace() {
        assertParse("\t", WhiteSpaceParselet.class, Arrays.asList('\t'));
        assertParse("    \r \t \nother string", 
                WhiteSpaceParselet.class, Arrays.asList(' ', ' ', ' ', ' ', '\r', ' ', '\t', ' ', '\n'));
        assertParse("no whitespace at the beginning", WhiteSpaceParselet.class, null);
    }
    
    @Test
    public void testComment() {
        assertParse("//something here", CommentParselet.class, (Comment) new LineComment("something here"));
        assertParse("/*more *complex**/", CommentParselet.class, (Comment) new BlockComment("more *complex*"));
        assertParse("/**javadoc time!*/", CommentParselet.class, (Comment) new JavadocComment("javadoc time!"));
        assertParse("this isn't a comment", CommentParselet.class, null);
        assertParse("//", CommentParselet.class, (Comment) new LineComment(""));
        assertParse("/**/", CommentParselet.class, (Comment) new BlockComment(""));
        assertParse("/***/", CommentParselet.class, (Comment) new JavadocComment(""));
        assertParse("//some here\nbut not here", CommentParselet.class, (Comment) new LineComment("some here"));
        assertParse("/*with\nnew\rlines*/", CommentParselet.class, (Comment) new BlockComment("with\nnew\rlines"));
        assertParse("/**with\nnew\rlines*/", CommentParselet.class, (Comment) new JavadocComment("with\nnew\rlines"));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testPackageDeclaration() {
        assertParse("package com.foo.bar;", PackageDeclarationParselet.class, 
                new PackageDeclaration(Collections.EMPTY_LIST, "com.foo.bar"));
        //TODO: when annotations are finished
    }
    
    private static void assertModifier(String name, int value) {
        //normal
        assertParse(name, ModifierParselet.class, value);
        //wrong w/ bad char
        assertParse("1" + name, ModifierParselet.class, null);
        //wrong w/ one char taken away
        assertParse(name.substring(0, name.length() - 1), ModifierParselet.class, null);
    }
    
    @Test
    public void testModifier() {
        assertModifier("public", Modifier.PUBLIC);
        assertModifier("static", Modifier.STATIC);
        assertModifier("protected", Modifier.PROTECTED);
        assertModifier("private", Modifier.PRIVATE);
        assertModifier("final", Modifier.FINAL);
        assertModifier("abstract", Modifier.ABSTRACT);
        assertModifier("synchronized", Modifier.SYNCHRONIZED);
        assertModifier("native", Modifier.NATIVE);
        assertModifier("transient", Modifier.TRANSIENT);
        assertModifier("volatile", Modifier.VOLATILE);
        assertModifier("strictfp", Modifier.STRICT);
    }
}
