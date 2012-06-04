package org.forsook.parser.java.ast.lexical;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;

@JlsReference("3.7")
@SuppressWarnings("serial")
public abstract class Comment extends JavaModel {

    private String text;
    
    public Comment() {
    }
    
    public Comment(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        Comment other = (Comment) obj;
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }
}
