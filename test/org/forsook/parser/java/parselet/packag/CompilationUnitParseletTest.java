package org.forsook.parser.java.parselet.packag;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Assert;
import org.junit.Test;

public class CompilationUnitParseletTest extends ParseletTestBase {

    @Test
    public void testSimpleCompilationUnit() {
        //just make sure it works first
        Parser parser = buildParser(streamToString(
                getClass().getResourceAsStream("SimpleCompilationUnit.java.txt")));
        CompilationUnit unit = parser.next(CompilationUnit.class);
        Assert.assertTrue(true);
    }
}
