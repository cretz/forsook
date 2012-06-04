package org.forsook.parser.java.ast.lexical;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;

@JlsReference("3.6")
@SuppressWarnings("serial")
public class WhiteSpace extends JavaModel {

    private WhiteSpaceType type;
    private int amount;
    
    public WhiteSpace() {
    }
    
    public WhiteSpace(WhiteSpaceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }
    
    public WhiteSpaceType getType() {
        return type;
    }
    
    public void setType(WhiteSpaceType type) {
        this.type = type;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        WhiteSpace other = (WhiteSpace) obj;
        if (amount != other.amount) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    public static enum WhiteSpaceType {
        SPACE,
        TAB,
        FORM_FEED,
        NEWLINE,
        RETURN,
        NEWLINE_RETURN
    }
}
