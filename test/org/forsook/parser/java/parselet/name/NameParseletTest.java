package org.forsook.parser.java.parselet.name;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class NameParseletTest extends ParseletTestBase {

    private List<Identifier> getIdentifierList(String... values) {
        List<Identifier> identifiers = new ArrayList<Identifier>();
        for (String value : values) {
            identifiers.add(new Identifier(value));
        }
        return identifiers;
    }
    
    @Test
    public void testQualifiedName() {
        assertParse("a.//something\nb", 
                new QualifiedName(getIdentifierList("a", "b"), false));
        assertParse("meh", new QualifiedName(getIdentifierList("meh"), false));
        assertParse("a.b.c.1", 
                new QualifiedName(getIdentifierList("a", "b", "c"), true));
        assertNull("1.2.3", QualifiedName.class);
    }
}
