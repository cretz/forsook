package org.forsook.parser.java.parselet.lexical;

import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class LiteralExpressionParseletTest extends ParseletTestBase {

    protected void assertParse(String source, LiteralExpressionType type) {
        assertParse(source, source, type);
    }

    protected void assertParse(String source, String expectedValue, LiteralExpressionType type) {
        assertParse(source, new LiteralExpression(expectedValue, type));
    }
    
    protected void assertNull(String source) {
        assertNull(source, LiteralExpression.class);
    }
    
    @Test
    public void testIntegerLiteralExpression() {
        //decimal integer literal
        assertParse("0", LiteralExpressionType.DECIMAL_INTEGER);
        assertNull("a");
        assertNull("_5");
        assertNull("5_");
        assertParse("5_0", LiteralExpressionType.DECIMAL_INTEGER);
        assertParse("5_0_1_6l", LiteralExpressionType.DECIMAL_INTEGER);
        assertParse("5_0_1_6L", LiteralExpressionType.DECIMAL_INTEGER);
        //these are some that barely miss other parts, so are just zeros here
        assertParse("0X_5", "0", LiteralExpressionType.DECIMAL_INTEGER);
        assertParse("0B_1", "0", LiteralExpressionType.DECIMAL_INTEGER);
        assertParse("0.e", "0", LiteralExpressionType.DECIMAL_INTEGER);
        assertParse("0.0e", "0", LiteralExpressionType.DECIMAL_INTEGER);
        assertParse("0e", "0", LiteralExpressionType.DECIMAL_INTEGER);
        //hex integer literal
        assertParse("0x0", LiteralExpressionType.HEX_INTEGER);
        assertParse("0x0__12_2", LiteralExpressionType.HEX_INTEGER);
        assertParse("0X5_5", LiteralExpressionType.HEX_INTEGER);
        //octal integer literal
        assertParse("02", LiteralExpressionType.OCTAL_INTEGER);
        assertParse("02_3", LiteralExpressionType.OCTAL_INTEGER);
        assertNull("02_3_");
        assertNull("08");
        //binary integer literal
        assertParse("0b1_0_1_0", LiteralExpressionType.BINARY_INTEGER);
        assertParse("0B11001", LiteralExpressionType.BINARY_INTEGER);
    }
    
    @Test
    public void testFloatingPointLiteralExpression() {
        //decimal floating point
        assertParse("0.", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse(".0", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0.0", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0.e5", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertNull(".0e");
        assertParse(".0e5", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0.0e5", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0.0e+5", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0.0e-5", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0e5", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0f", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0F", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0d", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        assertParse("0D", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        //stops at the decimal like it should
        assertParse("0e0.0", "0e0", LiteralExpressionType.DECIMAL_FLOATING_POINT);
        //hexadecimal floating point
        //TODO
    }
}
