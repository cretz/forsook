package org.forsook.parser.java.parselet;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.forsook.parser.java.ast.BlockComment;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.ImportDeclaration;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.LineComment;
import org.forsook.parser.java.ast.LiteralExpression;
import org.forsook.parser.java.ast.PackageDeclaration;
import org.forsook.parser.java.ast.PrimitiveType;
import org.forsook.parser.java.ast.LiteralExpression.LiteralExpressionType;
import org.forsook.parser.java.ast.PrimitiveType.Primitive;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.WildcardType;
import org.junit.Test;

public class SimpleParseletTest extends ParseletTestBase {

    @Test
    public void testWhiteSpace() {
        assertParse("\t", WhiteSpaceParselet.class, Arrays.asList('\t'));
        assertParse("    \r \t \nother string", 
                WhiteSpaceParselet.class, Arrays.asList(' ', ' ', ' ', ' ', '\r', ' ', '\t', ' ', '\n'));
        assertParse("no whitespace at the beginning", WhiteSpaceParselet.class, null);
    }
    
    @Test
    public void testComment() {
        assertParse("//something here", CommentParselet.class, (Comment) new LineComment("something here"));
        assertParse("/*more *complex**/", CommentParselet.class, (Comment) new BlockComment("more *complex*"));
        assertParse("/**javadoc time!*/", CommentParselet.class, (Comment) new JavadocComment("javadoc time!"));
        assertParse("this isn't a comment", CommentParselet.class, null);
        assertParse("//", CommentParselet.class, (Comment) new LineComment(""));
        assertParse("/**/", CommentParselet.class, (Comment) new BlockComment(""));
        assertParse("/***/", CommentParselet.class, (Comment) new JavadocComment(""));
        assertParse("//some here\nbut not here", CommentParselet.class, (Comment) new LineComment("some here"));
        assertParse("/*with\nnew\rlines*/", CommentParselet.class, (Comment) new BlockComment("with\nnew\rlines"));
        assertParse("/**with\nnew\rlines*/", CommentParselet.class, (Comment) new JavadocComment("with\nnew\rlines"));
    }
    
    @Test
    public void testQualifiedName() {
        assertParse("a.//something\nb", QualifiedNameParselet.class, "a.b");
        assertParse("meh", QualifiedNameParselet.class, "meh");
        assertParse("a.b.c.1", QualifiedNameParselet.class, "a.b.c.");
        assertParse("1.2.3", QualifiedNameParselet.class, null);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testPackageDeclaration() {
        assertParse("package com.foo.bar;", PackageDeclarationParselet.class, 
                new PackageDeclaration(Collections.EMPTY_LIST, "com.foo.bar"));
        //TODO: when annotations are finished
    }
    
    private static void assertModifier(String name, int value) {
        //normal
        assertParse(name, ModifierParselet.class, value);
        //wrong w/ bad char
        assertParse("1" + name, ModifierParselet.class, null);
        //wrong w/ one char taken away
        assertParse(name.substring(0, name.length() - 1), ModifierParselet.class, null);
    }
    
    @Test
    public void testModifier() {
        assertModifier("public", Modifier.PUBLIC);
        assertModifier("static", Modifier.STATIC);
        assertModifier("protected", Modifier.PROTECTED);
        assertModifier("private", Modifier.PRIVATE);
        assertModifier("final", Modifier.FINAL);
        assertModifier("abstract", Modifier.ABSTRACT);
        assertModifier("synchronized", Modifier.SYNCHRONIZED);
        assertModifier("native", Modifier.NATIVE);
        assertModifier("transient", Modifier.TRANSIENT);
        assertModifier("volatile", Modifier.VOLATILE);
        assertModifier("strictfp", Modifier.STRICT);
    }
    
    @Test
    public void testIdentifier() {
        //make sure all disallowed ones are disallowed
        for (String string : IdentifierParselet.DISALLOWED_IDENTIFIERS) {
            assertParse(string, IdentifierParselet.class, null);
        }
        assertParse("_testTest", IdentifierParselet.class, "_testTest");
        assertParse("_test.Test", IdentifierParselet.class, "_test");
        assertParse("._test.Test", IdentifierParselet.class, null);
        assertParse("133t", IdentifierParselet.class, null);
        assertParse("l33t", IdentifierParselet.class, "l33t");
        assertParse("test-test", IdentifierParselet.class, "test");
        assertParse("$test", IdentifierParselet.class, "$test");
        assertParse("t$es_$t", IdentifierParselet.class, "t$es_$t");
        assertParse("üö", IdentifierParselet.class, "üö");
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testClassOrInterfaceType() {
        assertParse("a.b.c", ClassOrInterfaceTypeParselet.class, 
                new ClassOrInterfaceType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        null, "a", Collections.EMPTY_LIST), "b", Collections.EMPTY_LIST), 
                        "c", Collections.EMPTY_LIST));
        assertParse("java.util.Map<K, V>", ClassOrInterfaceTypeParselet.class, 
                new ClassOrInterfaceType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        null, "java", Collections.EMPTY_LIST), "util", Collections.EMPTY_LIST), "Map", 
                        Arrays.asList(new ReferenceType(new ClassOrInterfaceType(null, "K", Collections.EMPTY_LIST), 0),
                            new ReferenceType(new ClassOrInterfaceType(null, "V", Collections.EMPTY_LIST), 0))));
        assertParse("Pair<A, Pair<A, /*comment*/ B>>", ClassOrInterfaceTypeParselet.class, 
                new ClassOrInterfaceType(null, "Pair", Arrays.asList(
                        new ReferenceType(new ClassOrInterfaceType(null, "A", Collections.EMPTY_LIST), 0),
                        new ReferenceType(new ClassOrInterfaceType(null, "Pair", 
                                Arrays.asList(
                                        new ReferenceType(new ClassOrInterfaceType(null, "A", Collections.EMPTY_LIST), 0),
                                        new ReferenceType(new ClassOrInterfaceType(null, "B", Collections.EMPTY_LIST), 0)
                                        )), 0))));
        assertParse("Pair<?, ?>", ClassOrInterfaceTypeParselet.class,
                new ClassOrInterfaceType(null, "Pair", Arrays.asList(
                        new WildcardType(null, null), new WildcardType(null, null))));
        assertParse("List<? extends List<?>>", ClassOrInterfaceTypeParselet.class,
                new ClassOrInterfaceType(null, "List", Arrays.asList(
                        new WildcardType(new ReferenceType(new ClassOrInterfaceType(null, "List", 
                                Arrays.asList(new WildcardType(null, null))), 0), null))));
        assertParse("List<? super List<?>>", ClassOrInterfaceTypeParselet.class,
                new ClassOrInterfaceType(null, "List", Arrays.asList(
                        new WildcardType(null, new ReferenceType(new ClassOrInterfaceType(null, "List", 
                                Arrays.asList(new WildcardType(null, null))), 0)))));
        assertParse("List<? extends ?>", ClassOrInterfaceTypeParselet.class, null);
    }
    
    @Test
    public void testImportDeclaration() {
        assertParse("import a.b.c;", ImportDeclarationParselet.class, 
                new ImportDeclaration("a.b.c", false, false));
        assertParse("import a./*comment*/b.c//blahblah\n;", ImportDeclarationParselet.class, 
                new ImportDeclaration("a.b.c", false, false));
        assertParse("import static a.b.c;", ImportDeclarationParselet.class, 
                new ImportDeclaration("a.b.c", true, false));
        assertParse("import static a.b.c.;", ImportDeclarationParselet.class, null);
        assertParse("import a.b//hey\n.c./*blahblah*/\n\n\r\n*;", ImportDeclarationParselet.class, 
                new ImportDeclaration("a.b.c.", false, true));
    }
    
    @Test
    public void testPrimitiveType() {
        for (Primitive primitive : Primitive.values()) {
            assertParse(primitive.name().toLowerCase(), PrimitiveTypeParselet.class, 
                    new PrimitiveType(primitive));
            assertParse(primitive.name().toLowerCase() + "1", PrimitiveTypeParselet.class, null);
            assertParse(primitive.name(), PrimitiveTypeParselet.class, null);
            assertParse("_" + primitive.name(), PrimitiveTypeParselet.class, null);
        }
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testReferenceType() {
        assertParse("A", ReferenceTypeParselet.class, new ReferenceType(
                new ClassOrInterfaceType(null, "A", Collections.EMPTY_LIST), 0));
        assertParse("A[/*something here*/]//meh\n[]", ReferenceTypeParselet.class, new ReferenceType(
                new ClassOrInterfaceType(null, "A", Collections.EMPTY_LIST), 2));
        assertParse("double[/*something here*/]//meh\n[]", ReferenceTypeParselet.class, new ReferenceType(
                new PrimitiveType(Primitive.DOUBLE), 2));
        assertParse("List<? extends List<?>>[]", ReferenceTypeParselet.class, new ReferenceType(
                new ClassOrInterfaceType(null, "List", Arrays.asList(
                        new WildcardType(new ReferenceType(new ClassOrInterfaceType(null, "List", 
                                Arrays.asList(new WildcardType(null, null))), 0), null))), 1));
    }
    
    @Test
    public void testLiteralExpression() {
        //decimal integer literal
        assertParse("0", LiteralExpressionParselet.class, 
                new LiteralExpression("0", LiteralExpressionType.DECIMAL_INTEGER));
//        assertParse("02", LiteralExpressionParselet.class, null);
//        assertParse("a", LiteralExpressionParselet.class, null);
//        assertParse("_5", LiteralExpressionParselet.class, null);
//        assertParse("5_", LiteralExpressionParselet.class, null);
//        assertParse("5_0", LiteralExpressionParselet.class,
//                new LiteralExpression("5_0", LiteralExpressionType.DECIMAL_INTEGER));
//        assertParse("5_0_1_6l", LiteralExpressionParselet.class,
//                new LiteralExpression("5_0_1_6l", LiteralExpressionType.DECIMAL_INTEGER));
//        assertParse("5_0_1_6L", LiteralExpressionParselet.class,
//                new LiteralExpression("5_0_1_6L", LiteralExpressionType.DECIMAL_INTEGER));
//        //hex integer literal
//        assertParse("0x0", LiteralExpressionParselet.class, 
//                new LiteralExpression("0x0", LiteralExpressionType.HEX_INTEGER));
//        assertParse("0x0__12_2", LiteralExpressionParselet.class, 
//                new LiteralExpression("0x0__12_2", LiteralExpressionType.HEX_INTEGER));
        
    }
}
