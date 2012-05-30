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
}
