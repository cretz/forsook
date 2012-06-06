package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.java.ast.lexical.BlockComment;
import org.forsook.parser.java.ast.lexical.Comment;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.lexical.LineComment;
import org.forsook.parser.java.ast.lexical.WhiteSpace;
import org.forsook.parser.java.ast.lexical.WhiteSpace.WhiteSpaceType;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class LexicalParseletTest extends ParseletTestBase {

    @Test
    public void testWhiteSpace() {
        assertParse("\t\t\t\f", new WhiteSpace(WhiteSpaceType.TAB, 3));
        assertParse("    \f", new WhiteSpace(WhiteSpaceType.SPACE, 4));
        assertParse("\f\t", new WhiteSpace(WhiteSpaceType.FORM_FEED, 1));
        assertParse("\n\n\n\f", new WhiteSpace(WhiteSpaceType.NEWLINE, 3));
        assertParse("\r\r\f", new WhiteSpace(WhiteSpaceType.RETURN, 2));
        assertParse("\r\n\r\n\f", new WhiteSpace(WhiteSpaceType.NEWLINE_RETURN, 2));
        assertNull("no whitespace at the beginning", WhiteSpace.class);
    }
    
    @Test
    public void testComment() {
        assertParse("//something here", Comment.class, new LineComment("something here"));
        assertParse("/*more *complex**/", Comment.class, new BlockComment("more *complex*"));
        assertParse("/**javadoc time!*/", Comment.class, new JavadocComment("javadoc time!"));
        assertNull("this isn't a comment", Comment.class);
        assertParse("//", Comment.class, new LineComment(""));
        assertParse("/**/", Comment.class, new BlockComment(""));
        assertParse("/***/", Comment.class, new JavadocComment(""));
        assertParse("//some here\nbut not here", Comment.class, new LineComment("some here"));
        assertParse("/*with\nnew\rlines*/", Comment.class, new BlockComment("with\nnew\rlines"));
        assertParse("/**with\nnew\rlines*/", Comment.class, new JavadocComment("with\nnew\rlines"));
    }
    
    @Test
    public void testIdentifier() {
        //make sure all disallowed ones are disallowed
        for (String string : IdentifierParselet.DISALLOWED_IDENTIFIERS) {
            assertNull(string, Identifier.class);
        }
        assertParse("_testTest", new Identifier("_testTest"));
        assertParse("_test.Test", new Identifier("_test"));
        assertNull("._test.Test", Identifier.class);
        assertNull("133t", Identifier.class);
        assertParse("l33t", new Identifier("l33t"));
        assertParse("test-test", new Identifier("test"));
        assertParse("$test", new Identifier("$test"));
        assertParse("t$es_$t", new Identifier("t$es_$t"));
        assertParse("üö", new Identifier("üö"));
    }
    
}
