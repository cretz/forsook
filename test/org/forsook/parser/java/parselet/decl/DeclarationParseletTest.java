package org.forsook.parser.java.parselet.decl;

import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.packag.ImportDeclaration;
import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class DeclarationParseletTest extends ParseletTestBase {

    @Test
    public void testPackageDeclaration() {
        assertString("package com.foo.bar;", PackageDeclaration.class);
    }
    
    private void assertModifier(String name, Modifier value) {
        //normal
        assertString(name, Modifier.class);
        //wrong w/ bad char
        assertNull("1" + name, Modifier.class);
        //wrong w/ one char taken away
        assertNull(name.substring(0, name.length() - 1), Modifier.class);
    }
    
    @Test
    public void testModifier() {
        for (Modifier modifier : Modifier.values()) {
            assertModifier(modifier.getLowerCase(), modifier);
        }
    }
    
    @Test
    public void testImportDeclaration() {
        assertString("import single.type.mport.Declaration;", ImportDeclaration.class);
        assertString("import type.on.demand.mport.declaration.*;", ImportDeclaration.class);
        assertString("import static single.stat.mport.Declaration;", ImportDeclaration.class);
        assertString("import static stat.on.demand.mport.declaration.*;", ImportDeclaration.class);
    }
    
    @Test
    public void testVariableDeclarator() {
        assertString("i = 1 + 1", VariableDeclarator.class);
    }
    
    @Test
    public void testAnnotationTypeDeclaration() {
        assertString("public @interface MyAnnotation {\n\n" +
                "    String value() default \"\";\n" +
                "}", AnnotationTypeDeclaration.class);
    }
    
    @Test
    public void testConstructorDeclaration() {
        assertString(
                "public MyClassName() {\n" +
                "    this();\n" +
                "}", ConstructorDeclaration.class);
    }
    
    @Test
    public void testEnum() {
        assertString(
                "public enum MyEnum {\n" +
                "    VAL;\n" +
                "}", EnumDeclaration.class);
    }
    
    @Test
    public void testFieldDeclaration() {
        assertString("Map map = new HashMap();", FieldDeclaration.class);
    }
    
    public void testClassOrInterfaceBody() {
        assertString(
                "{\n" +
                "    void a() {\n" +
                "    }\n\n" +
                "    d e = { };\n" +
                "}", ClassOrInterfaceBody.class);
    }
}
