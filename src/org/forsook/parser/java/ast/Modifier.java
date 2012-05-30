package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("6.6") //TODO: add the rest
@SuppressWarnings("serial")
public class Modifier extends JavaModel {

    private int modifier;
    
    public Modifier() {
    }
    
    public Modifier(int modifier) {
        this.modifier = modifier;
    }
    
    public int getModifier() {
        return modifier;
    }
    
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
}
