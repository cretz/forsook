package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("3.7")
@SuppressWarnings("serial")
public class JavadocComment extends Comment {

    public JavadocComment() {
    }
    
    public JavadocComment(String text) {
        super(text);
    }
}
