package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("3.8")
@SuppressWarnings("serial")
public class Identifier extends JavaModel {

    private String value;
    
    public Identifier() {
    }
    
    public Identifier(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Identifier other = (Identifier) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
