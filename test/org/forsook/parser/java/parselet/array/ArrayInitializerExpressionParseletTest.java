package org.forsook.parser.java.parselet.array;

import org.forsook.parser.java.ast.array.ArrayInitializerExpression;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class ArrayInitializerExpressionParseletTest extends ParseletTestBase {

    @Test
    public void testArrayInitializerExpression() {
        assertString("{ 1, { 2, 3 } }", ArrayInitializerExpression.class);
    }
}
