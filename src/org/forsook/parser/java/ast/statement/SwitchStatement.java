package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.11")
@SuppressWarnings("serial")
public class SwitchStatement extends Statement implements StatementWithoutTrailingSubstatement {

    private Expression selector;
    private List<SwitchBlockStatementGroup> entries;
    
    public SwitchStatement() {
    }
    
    public SwitchStatement(Expression selector, List<SwitchBlockStatementGroup> entries) {
        this.selector = selector;
        this.entries = entries;
    }
    
    public Expression getSelector() {
        return selector;
    }
    
    public void setSelector(Expression selector) {
        this.selector = selector;
    }
    
    public List<SwitchBlockStatementGroup> getEntries() {
        return entries;
    }
    
    public void setEntries(List<SwitchBlockStatementGroup> entries) {
        this.entries = entries;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result
                + ((selector == null) ? 0 : selector.hashCode());
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
        SwitchStatement other = (SwitchStatement) obj;
        if (entries == null) {
            if (other.entries != null) {
                return false;
            }
        } else if (!entries.equals(other.entries)) {
            return false;
        }
        if (selector == null) {
            if (other.selector != null) {
                return false;
            }
        } else if (!selector.equals(other.selector)) {
            return false;
        }
        return true;
    }
}
