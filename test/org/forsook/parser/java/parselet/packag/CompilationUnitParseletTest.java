package org.forsook.parser.java.parselet.packag;

import java.io.InputStream;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.forsook.util.IoUtils;
import org.junit.Assert;
import org.junit.Test;

public class CompilationUnitParseletTest extends ParseletTestBase {

    @Test
    @SuppressWarnings("unused")
    public void testComplexCompilationUnit() {
        //just make sure it works first
        Parser parser = buildParser(IoUtils.streamToString(
                getClass().getResourceAsStream("ComplexCompilationUnit.java.txt")));
        CompilationUnit unit = parser.next(CompilationUnit.class);
        //System.out.print(new JavaSourceWriter().build(unit));
        if (!parser.isPeekEndOfInput()) {
            throw new RuntimeException("Unable to parse " +
                    parser.getLine() + ":" + parser.getColumn());
        }
        Assert.assertTrue(true);
    }
    
    @Test
    public void testCompilationUnit() {
        for (int i = 1; ; i++) {
            InputStream stream = getClass().getResourceAsStream(
                    "CompilationUnit." + i + ".java.txt");
            if (stream == null) {
                break;
            }
            String source = IoUtils.streamToString(stream);
            assertString(source.replace("\r\n", "\n"), CompilationUnit.class);
        }
    }
}
