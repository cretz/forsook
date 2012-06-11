package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;

@JlsReference({ "8.1.1", "8.3.1", "8.4.1", "8.4.3", "8.8.3", "9.1.1", "9.3", "9.4" })
public enum Modifier {

    PUBLIC(java.lang.reflect.Modifier.PUBLIC),
    PRIVATE(java.lang.reflect.Modifier.PRIVATE),
    PROTECTED(java.lang.reflect.Modifier.PROTECTED),
    STATIC(java.lang.reflect.Modifier.STATIC),
    FINAL(java.lang.reflect.Modifier.FINAL),
    SYNCHRONIZED(java.lang.reflect.Modifier.SYNCHRONIZED),
    VOLATILE(java.lang.reflect.Modifier.VOLATILE),
    TRANSIENT(java.lang.reflect.Modifier.TRANSIENT),
    NATIVE(java.lang.reflect.Modifier.NATIVE),
    ABSTRACT(java.lang.reflect.Modifier.ABSTRACT),
    STRICTFP(java.lang.reflect.Modifier.STRICT);
    
    private final int modifier;
    private final String lowerCase;
    
    private Modifier(int modifier) {
        this.modifier = modifier;
        this.lowerCase = name().toLowerCase();
    }
    
    public int getModifier() {
        return modifier;
    }
    
    public String getLowerCase() {
        return lowerCase;
    }
}
