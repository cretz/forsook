package org.forsook.parser.java.ast.lexical;

import org.forsook.parser.java.JlsReference;

@JlsReference("3.7")
@SuppressWarnings("serial")
public class BlockComment extends Comment {

    public BlockComment() {
    }
    
    public BlockComment(String text) {
        super(text);
    }
}
