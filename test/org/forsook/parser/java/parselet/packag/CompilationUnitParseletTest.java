package org.forsook.parser.java.parselet.packag;

import org.forsook.parser.Parser;
import org.forsook.parser.java.JavaSourceWriter;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Assert;
import org.junit.Test;

public class CompilationUnitParseletTest extends ParseletTestBase {

    @Test
    public void testComplexCompilationUnit() {
        //just make sure it works first
        Parser parser = buildParser(streamToString(
                getClass().getResourceAsStream("ComplexCompilationUnit.java.txt")));
        CompilationUnit unit = parser.next(CompilationUnit.class);
        System.out.print(new JavaSourceWriter().build(unit));
        if (!parser.isPeekEndOfInput()) {
            throw new RuntimeException("Unable to parse " +
                    parser.getLine() + ":" + parser.getColumn());
        }
        Assert.assertTrue(true);
    }
}
