package org.forsook.parser.java.parselet.decl;

import java.util.Collections;

import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
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
    
    private void assertModifier(String name, Modifier value) {
        //normal
        assertParse(name, value);
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
    @Ignore("TODO")
    public void testImportDeclaration() {
        //TODO
    }
    
    @Test
    public void testVariableDeclarator() {
        assertString("i = 1 + 1", VariableDeclarator.class);
    }
}
