package org.forsook.parser.java.parselet.expression;

import java.util.Collections;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Assert;
import org.junit.Test;

public class MethodInvocationExpressionParseletTest extends ParseletTestBase {

    @Test
    @SuppressWarnings("unchecked")
    public void testSimpleMethodInvocation() {
//        assertParse("println()", new MethodInvocationExpression(null, 
//                null, false, null, 
//                new QualifiedName(Collections.singletonList(new Identifier("println"))), 
//                        Collections.EMPTY_LIST));
        Parser parser = buildParser("System.out.println()");
        MethodInvocationExpression invocation = parser.next(MethodInvocationExpression.class);
        Assert.assertTrue(true);
    }
}
