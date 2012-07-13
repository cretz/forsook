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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + sourceIndex;
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
        JavaModel other = (JavaModel) obj;
        if (sourceIndex != other.sourceIndex) {
            return false;
        }
        return true;
    }
}
