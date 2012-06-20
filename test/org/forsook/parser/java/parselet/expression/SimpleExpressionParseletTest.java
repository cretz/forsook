package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationStatement;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class SimpleExpressionParseletTest extends ParseletTestBase {

    @Test
    public void testAdditiveOperatorExpression() {
        assertString("1 + 1", AdditiveOperatorExpression.class);
    }

    @Test
    public void testLocalVariableDeclarationStatement() {
        assertString("int i;", LocalVariableDeclarationStatement.class);
        assertString("int i = 1 + 1;", LocalVariableDeclarationStatement.class);
    }
}
