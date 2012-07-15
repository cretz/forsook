package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.java.ast.lexical.Comment;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.WhiteSpace;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class LexicalParseletTest extends ParseletTestBase {

    @Test
    public void testWhiteSpace() {
        assertString("\t\t\t", WhiteSpace.class);
        assertString("    ", WhiteSpace.class);
        assertString("\f\f", WhiteSpace.class);
        assertString("\n\n\n", WhiteSpace.class);
        assertString("\r\r", WhiteSpace.class);
        assertString("\r\n\r\n", WhiteSpace.class);
    }
    
    @Test
    public void testComment() {
        assertString("//something here", Comment.class);
        assertString("/*more *complex**/", Comment.class);
        assertString("/**javadoc time!*/", Comment.class);
        assertNull("notAComment()", Comment.class);
        assertString("//", Comment.class);
        assertString("/**/", Comment.class);
        assertString("/***/", Comment.class);
        assertString("/*with\nnew\rlines*/", Comment.class);
        assertString("/**with\nnew\rlines*/", Comment.class);
    }
    
    @Test
    public void testIdentifier() {
        //make sure all disallowed ones are disallowed
        for (String string : IdentifierParselet.DISALLOWED_IDENTIFIERS) {
            assertNull(string, Identifier.class);
        }
        assertString("_testTest", Identifier.class);
        assertNull("._test.Test", Identifier.class);
        assertNull("133t", Identifier.class);
        assertString("l33t", Identifier.class);
        assertString("$test", Identifier.class);
        assertString("t$es_$t", Identifier.class);
        assertString("üö", Identifier.class);
    }
    
}
