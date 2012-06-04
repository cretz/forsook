package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("14.15")
@SuppressWarnings("serial")
public class BreakStatement extends Statement implements StatementWithoutTrailingSubstatement {

    private Identifier label;
    
    public BreakStatement() {
    }
    
    public BreakStatement(Identifier label) {
        this.label = label;
    }
    
    public Identifier getLabel() {
        return label;
    }
    
    public void setLabel(Identifier label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        BreakStatement other = (BreakStatement) obj;
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        } else if (!label.equals(other.label)) {
            return false;
        }
        return true;
    }
    
    
}
