package org.forsook.parser.java.ast.lexical;

import org.forsook.parser.java.JlsReference;

@JlsReference("3.7")
@SuppressWarnings("serial")
public class LineComment extends Comment {

    public LineComment() {
        
    }
    
    public LineComment(String text) {
        super(text);
    }
}
