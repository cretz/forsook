package org.forsook.parser.java.parselet.statement;

import org.forsook.parser.java.ast.statement.AssertStatement;
import org.forsook.parser.java.ast.statement.DoStatement;
import org.forsook.parser.java.ast.statement.EmptyStatement;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.ForStatement;
import org.forsook.parser.java.ast.statement.IfStatement;
import org.forsook.parser.java.ast.statement.LabeledStatement;
import org.forsook.parser.java.ast.statement.ReturnStatement;
import org.forsook.parser.java.ast.statement.SwitchStatement;
import org.forsook.parser.java.ast.statement.ThrowStatement;
import org.forsook.parser.java.ast.statement.TryStatement;
import org.forsook.parser.java.ast.statement.WhileStatement;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class StatementParseletTest extends ParseletTestBase {
    
    @Test
    public void testEmptyStatement() {
        assertString(";", EmptyStatement.class);
    }
    
    @Test
    public void testLabeledStatement() {
        assertString("alabel: ;", LabeledStatement.class);
    }
    
    @Test
    public void testWhileStatement() {
        assertString("while (true) ;", WhileStatement.class);
    }
    
    @Test
    public void testDoStatement() {
        assertString("do \n    ;\nwhile (true);", DoStatement.class);
    }
    
    @Test
    public void testIfStatement() {
        assertString("if (true) \n    ;", IfStatement.class);
    }
    
    @Test
    public void testForStatement() {
        assertString("for (int i = 0; i < 10; i++) \n    ;", ForStatement.class);
        assertString("for (String foo : bar) \n    ;", ForStatement.class);
    }
    
    @Test
    public void testTryStatement() {
        assertString("try {\n    ;\n} catch (Exception e) {\n}", TryStatement.class);
    }
    
    @Test
    public void testAssertStatement() {
        assertString("assert true;", AssertStatement.class);
        assertString("assert true : \"foo\";", AssertStatement.class);
    }
    
    @Test
    public void testSwitchStatement() {
        assertString("switch (val) {\n" +
                "case 1:\n" +
                "case 2:\n" +
                "    break;\n" +
                "default:\n" +
                "    break;\n" +
                "}", SwitchStatement.class);
    }
    
    @Test
    public void testThrowStatement() {
        assertString("throw new Exception(\"hey\");", ThrowStatement.class);
    }
    
    @Test
    public void testReturnStatement() {
        assertString("return \"meh\";", ReturnStatement.class);
    }
    
    @Test
    public void testExpressionStatement() {
        assertString("i++;", ExpressionStatement.class);
    }
}
