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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + modifier;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Modifier other = (Modifier) obj;
        if (modifier != other.modifier) {
            return false;
        }
        return true;
    }
}
