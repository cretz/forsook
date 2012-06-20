package org.forsook.parser.java.ast;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class JavaModel implements Serializable {

    private int sourceIndex;
    
    public int getSourceIndex() {
        return sourceIndex;
    }
    
    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }
}
