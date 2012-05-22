package org.forsook.parser.java.parselet;

import java.util.Arrays;

import org.forsook.parser.java.ast.BlockComment;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.LineComment;
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
}
