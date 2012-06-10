package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Assert;
import org.junit.Test;

public class MethodInvocationExpressionParseletTest extends ParseletTestBase {

    @Test
    public void testSimpleMethodInvocation() {
        Parser parser = buildParser("println()");
        MethodInvocationExpression invocation = parser.next(MethodInvocationExpression.class);
        Assert.assertTrue(true);
    }
}
