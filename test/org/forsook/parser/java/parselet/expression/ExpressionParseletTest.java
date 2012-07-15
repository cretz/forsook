package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression;
import org.forsook.parser.java.ast.expression.AndOperatorExpression;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression.AssignmentOperator;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.ClassExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrOperatorExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.InclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.NegatedExpression;
import org.forsook.parser.java.ast.expression.ParenthesizedExpression;
import org.forsook.parser.java.ast.expression.PrefixIncrementExpression;
import org.forsook.parser.java.ast.expression.SignedExpression;
import org.forsook.parser.java.ast.expression.ThisExpression;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationStatement;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class ExpressionParseletTest extends ParseletTestBase {

    @Test
    public void testCommonExpressions() {
        assertString("1 == 1", EqualityOperatorExpression.class);
        assertString("1 != 1", EqualityOperatorExpression.class);
        assertString("1 + 1", AdditiveOperatorExpression.class);
        assertString("1 - 1", AdditiveOperatorExpression.class);
        assertString("1 & 1", AndOperatorExpression.class);
        assertString("true && false", ConditionalAndOperatorExpression.class);
        assertString("true || false", ConditionalOrOperatorExpression.class);
        assertString("1 ^ 1", ExclusiveOrOperatorExpression.class);
        assertString("1 | 1", InclusiveOrOperatorExpression.class);
        assertString("!true", NegatedExpression.class);
        assertString("~1", NegatedExpression.class);
        assertString("++i", PrefixIncrementExpression.class);
        assertString("--i", PrefixIncrementExpression.class);
        assertString("+i", SignedExpression.class);
        assertString("-i", SignedExpression.class);
        assertString("(something++)", ParenthesizedExpression.class);
        assertString("Something.this", ThisExpression.class);
        assertString("this", ThisExpression.class);
        assertString("i[5]", ArrayAccessExpression.class);
        assertString("(int) i", CastExpression.class);
    }
    
    @Test
    public void testAssignmentOperatorExpression() {
        for (AssignmentOperator operator : AssignmentOperator.values()) {
            assertString("i " + operator.toString() + " 5", AssignmentOperatorExpression.class);
        }
        assertString("i = (SomeClass) j", AssignmentOperatorExpression.class);
    }

    @Test
    public void testLocalVariableDeclarationStatement() {
        assertString("int i;", LocalVariableDeclarationStatement.class);
        assertString("int i = 1 + 1;", LocalVariableDeclarationStatement.class);
    }
    
    @Test
    public void testClassExpression() {
        assertString("Integer.class", ClassExpression.class);
    }
    
    @Test
    public void testFieldAccessExpression() {
        assertString("a.b.c", FieldAccessExpression.class);
    }
    
    @Test
    public void testMethodInvocationExpression() {
        assertString("System.out.println()", MethodInvocationExpression.class);
//        assertString("println(new SomeClass().method())", 
//                MethodInvocationExpression.class);
    }
    
    @Test
    public void testCastExpression() {
        assertString("(SomeClass) a", CastExpression.class);
    }
}
