package org.forsook.parser.java.parselet;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.forsook.parser.java.ast.BlockComment;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.ImportDeclaration;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.LineComment;
import org.forsook.parser.java.ast.PackageDeclaration;
import org.forsook.parser.java.ast.PrimitiveType;
import org.forsook.parser.java.ast.PrimitiveType.Primitive;
import org.forsook.parser.java.ast.QualifiedName;
import org.forsook.parser.java.ast.ReferenceType;
import org.forsook.parser.java.ast.WhiteSpace;
import org.forsook.parser.java.ast.WhiteSpace.WhiteSpaceType;
import org.forsook.parser.java.ast.WildcardType;
import org.junit.Test;

public class SimpleParseletTest extends ParseletTestBase {

    @Test
    public void testWhiteSpace() {
        assertParse("\t\t\t\f", new WhiteSpace(WhiteSpaceType.TAB, 3));
        assertParse("    \f", new WhiteSpace(WhiteSpaceType.SPACE, 4));
        assertParse("\f\t", new WhiteSpace(WhiteSpaceType.FORM_FEED, 1));
        assertParse("\n\n\n\f", new WhiteSpace(WhiteSpaceType.NEWLINE, 3));
        assertParse("\r\r\f", new WhiteSpace(WhiteSpaceType.RETURN, 2));
        assertParse("\r\n\r\n\f", new WhiteSpace(WhiteSpaceType.NEWLINE_RETURN, 2));
        assertNull("no whitespace at the beginning", WhiteSpace.class);
    }
    
    @Test
    public void testComment() {
        assertParse("//something here", Comment.class, new LineComment("something here"));
        assertParse("/*more *complex**/", Comment.class, new BlockComment("more *complex*"));
        assertParse("/**javadoc time!*/", Comment.class, new JavadocComment("javadoc time!"));
        assertNull("this isn't a comment", Comment.class);
        assertParse("//", Comment.class, new LineComment(""));
        assertParse("/**/", Comment.class, new BlockComment(""));
        assertParse("/***/", Comment.class, new JavadocComment(""));
        assertParse("//some here\nbut not here", Comment.class, new LineComment("some here"));
        assertParse("/*with\nnew\rlines*/", Comment.class, new BlockComment("with\nnew\rlines"));
        assertParse("/**with\nnew\rlines*/", Comment.class, new JavadocComment("with\nnew\rlines"));
    }
    
    @Test
    public void testQualifiedName() {
        assertParse("a.//something\nb", new QualifiedName("a.b"));
        assertParse("meh", new QualifiedName("meh"));
        assertParse("a.b.c.1", new QualifiedName("a.b.c."));
        assertNull("1.2.3", QualifiedName.class);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testPackageDeclaration() {
        assertParse("package com.foo.bar;", 
                new PackageDeclaration(Collections.EMPTY_LIST, new QualifiedName("com.foo.bar")));
        //TODO: when annotations are finished
    }
    
    private void assertModifier(String name, int value) {
        //normal
        assertParse(name, new org.forsook.parser.java.ast.Modifier(value));
        //wrong w/ bad char
        assertNull("1" + name, org.forsook.parser.java.ast.Modifier.class);
        //wrong w/ one char taken away
        assertNull(name.substring(0, name.length() - 1), org.forsook.parser.java.ast.Modifier.class);
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
            assertNull(string, Identifier.class);
        }
        assertParse("_testTest", new Identifier("_testTest"));
        assertParse("_test.Test", new Identifier("_test"));
        assertNull("._test.Test", Identifier.class);
        assertNull("133t", Identifier.class);
        assertParse("l33t", new Identifier("l33t"));
        assertParse("test-test", new Identifier("test"));
        assertParse("$test", new Identifier("$test"));
        assertParse("t$es_$t", new Identifier("t$es_$t"));
        assertParse("üö", new Identifier("üö"));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testClassOrInterfaceType() {
        assertParse("a.b.c",  
                new ClassOrInterfaceType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        null, new Identifier("a"), Collections.EMPTY_LIST), 
                        new Identifier("b"), Collections.EMPTY_LIST), new Identifier("c"), Collections.EMPTY_LIST));
        assertParse("java.util.Map<K, V>",  
                new ClassOrInterfaceType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        null, new Identifier("java"), Collections.EMPTY_LIST), new Identifier("util"), Collections.EMPTY_LIST), 
                        new Identifier("Map"), Arrays.asList(new ReferenceType(new ClassOrInterfaceType(
                                null, new Identifier("K"), Collections.EMPTY_LIST), 0), new ReferenceType(
                                        new ClassOrInterfaceType(null, new Identifier("V"), Collections.EMPTY_LIST), 0))));
        assertParse("Pair<A, Pair<A, /*comment*/ B>>", 
                new ClassOrInterfaceType(null, new Identifier("Pair"), Arrays.asList(
                        new ReferenceType(new ClassOrInterfaceType(null, new Identifier("A"), Collections.EMPTY_LIST), 0),
                        new ReferenceType(new ClassOrInterfaceType(null, new Identifier("Pair"), Arrays.asList(
                                new ReferenceType(new ClassOrInterfaceType(null, new Identifier("A"), Collections.EMPTY_LIST), 0),
                                new ReferenceType(new ClassOrInterfaceType(null, new Identifier("B"), Collections.EMPTY_LIST), 0)
                                        )), 0))));
        assertParse("Pair<?, ?>",
                new ClassOrInterfaceType(null, new Identifier("Pair"), Arrays.asList(
                        new WildcardType(null, null), new WildcardType(null, null))));
        assertParse("List<? extends List<?>>",
                new ClassOrInterfaceType(null, new Identifier("List"), Arrays.asList(
                        new WildcardType(new ReferenceType(new ClassOrInterfaceType(null, new Identifier("List"), 
                                Arrays.asList(new WildcardType(null, null))), 0), null))));
        assertParse("List<? super List<?>>", 
                new ClassOrInterfaceType(null, new Identifier("List"), Arrays.asList(
                        new WildcardType(null, new ReferenceType(new ClassOrInterfaceType(null, new Identifier("List"), 
                                Arrays.asList(new WildcardType(null, null))), 0)))));
        assertNull("List<? extends ?>", ClassOrInterfaceType.class);
    }
    
    @Test
    public void testImportDeclaration() {
        assertParse("import a.b.c;", 
                new ImportDeclaration(new QualifiedName("a.b.c"), false, false));
        assertParse("import a./*comment*/b.c//blahblah\n;",  
                new ImportDeclaration(new QualifiedName("a.b.c"), false, false));
        assertParse("import static a.b.c;",  
                new ImportDeclaration(new QualifiedName("a.b.c"), true, false));
        assertNull("import static a.b.c.;", ImportDeclaration.class);
        assertParse("import a.b//hey\n.c./*blahblah*/\n\n\r\n*;",  
                new ImportDeclaration(new QualifiedName("a.b.c."), false, true));
    }
    
    @Test
    public void testPrimitiveType() {
        for (Primitive primitive : Primitive.values()) {
            assertParse(primitive.name().toLowerCase(), new PrimitiveType(primitive));
            assertNull(primitive.name().toLowerCase() + "1", PrimitiveType.class);
            assertNull(primitive.name(), PrimitiveType.class);
            assertNull("_" + primitive.name(), PrimitiveType.class);
        }
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testReferenceType() {
        assertParse("A", new ReferenceType(
                new ClassOrInterfaceType(null, new Identifier("A"), Collections.EMPTY_LIST), 0));
        assertParse("A[/*something here*/]//meh\n[]", new ReferenceType(
                new ClassOrInterfaceType(null, new Identifier("A"), Collections.EMPTY_LIST), 2));
        assertParse("double[/*something here*/]//meh\n[]", new ReferenceType(
                new PrimitiveType(Primitive.DOUBLE), 2));
        assertParse("List<? extends List<?>>[]", new ReferenceType(
                new ClassOrInterfaceType(null, new Identifier("List"), Arrays.asList(
                        new WildcardType(new ReferenceType(new ClassOrInterfaceType(null, 
                                new Identifier("List"), Arrays.asList(new WildcardType(null, null))), 0), null))), 1));
    }
}
