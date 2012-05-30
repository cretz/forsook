package org.forsook.parser.java.parselet;

import org.forsook.parser.java.ast.LiteralExpression;
import org.forsook.parser.java.ast.LiteralExpression.LiteralExpressionType;
import org.junit.Test;

public class LiteralExpressionParseletTest extends ParseletTestBase {

    @Test
    public void testIntegerLiteralExpression() {
        //decimal integer literal
        assertParse("0", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("0", LiteralExpressionType.DECIMAL_INTEGER));
        assertParse("a", IntegerLiteralExpressionParselet.class, null);
        assertParse("_5", IntegerLiteralExpressionParselet.class, null);
        assertParse("5_", IntegerLiteralExpressionParselet.class, null);
        assertParse("5_0", IntegerLiteralExpressionParselet.class,
                new LiteralExpression("5_0", LiteralExpressionType.DECIMAL_INTEGER));
        assertParse("5_0_1_6l", IntegerLiteralExpressionParselet.class,
                new LiteralExpression("5_0_1_6l", LiteralExpressionType.DECIMAL_INTEGER));
        assertParse("5_0_1_6L", IntegerLiteralExpressionParselet.class,
                new LiteralExpression("5_0_1_6L", LiteralExpressionType.DECIMAL_INTEGER));
        //technically decimal because it doesn't match hex and therefore stops at "X"
        assertParse("0X_5", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("0", LiteralExpressionType.DECIMAL_INTEGER));
        assertParse("0B_1", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("0", LiteralExpressionType.DECIMAL_INTEGER));
        //hex integer literal
        assertParse("0x0", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("0x0", LiteralExpressionType.HEX_INTEGER));
        assertParse("0x0__12_2", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("0x0__12_2", LiteralExpressionType.HEX_INTEGER));
        assertParse("0X5_5", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("0X5_5", LiteralExpressionType.HEX_INTEGER));
        //octal integer literal
        assertParse("02", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("02", LiteralExpressionType.OCTAL_INTEGER));
        assertParse("02_3", IntegerLiteralExpressionParselet.class, 
                new LiteralExpression("02_3", LiteralExpressionType.OCTAL_INTEGER));
        assertParse("02_3_", IntegerLiteralExpressionParselet.class, null);
        assertParse("08", IntegerLiteralExpressionParselet.class, null);
        //binary integer literal
        assertParse("0b1_0_1_0", IntegerLiteralExpressionParselet.class,
                new LiteralExpression("0b1_0_1_0", LiteralExpressionType.BINARY_INTEGER));
        assertParse("0B11001", IntegerLiteralExpressionParselet.class,
                new LiteralExpression("0B11001", LiteralExpressionType.BINARY_INTEGER));
    }
    
    @Test
    public void testFloatingPointLiteralExpression() {
        //decimal floating point
        assertParse("0.", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0.", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse(".0", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression(".0", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0.0", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0.0", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0.e", FloatingPointLiteralExpressionParselet.class, null);
        assertParse("0.e5", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0.e5", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse(".0e", FloatingPointLiteralExpressionParselet.class, null);
        assertParse(".0e5", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression(".0e5", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0.0e", FloatingPointLiteralExpressionParselet.class, null);
        assertParse("0.0e5", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0.0e5", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0.0e+5", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0.0e+5", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0.0e-5", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0.0e-5", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0e", FloatingPointLiteralExpressionParselet.class, null);
        assertParse("0e5", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0e5", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0f", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0f", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0F", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0F", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0d", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0d", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0D", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0D", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        assertParse("0q", FloatingPointLiteralExpressionParselet.class, null);
        //stops at the decimal like it should
        assertParse("0e0.0", FloatingPointLiteralExpressionParselet.class,
                new LiteralExpression("0e0", LiteralExpressionType.DECIMAL_FLOATING_POINT));
        //hexadecimal floating point
        //TODO
    }
}
