package org.forsook.parser.java.parselet.decl;

import java.lang.reflect.Modifier;
import java.util.Collections;

import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Ignore;
import org.junit.Test;

public class DeclarationParseletTest extends ParseletTestBase {

    @Test
    @SuppressWarnings("unchecked")
    public void testPackageDeclaration() {
        assertParse("package com.foo.bar;", 
                new PackageDeclaration(Collections.EMPTY_LIST, 
                getQualifiedName("com.foo.bar")));
        //TODO: annotations
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
    @Ignore("TODO")
    public void testImportDeclaration() {
        //TODO
    }
}
