package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.11")
@SuppressWarnings("serial")
public class SwitchBlockStatementGroup extends JavaModel {

    private List<Expression> labels;
    private boolean defaultPresent;
    private List<Statement> statements;
    
    public SwitchBlockStatementGroup() {
    }
    
    public SwitchBlockStatementGroup(List<Expression> labels, boolean defaultPresent,
            List<Statement> statements) {
        this.labels = labels;
        this.defaultPresent = defaultPresent;
        this.statements = statements;
    }
    
    public List<Expression> getLabels() {
        return labels;
    }
    
    public void setLabels(List<Expression> labels) {
        this.labels = labels;
    }
    
    public boolean isDefaultPresent() {
        return defaultPresent;
    }
    
    public void setDefaultPresent(boolean defaultPresent) {
        this.defaultPresent = defaultPresent;
    }
    
    public List<Statement> getStatements() {
        return statements;
    }
    
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (defaultPresent ? 1231 : 1237);
        result = prime * result + ((labels == null) ? 0 : labels.hashCode());
        result = prime * result
                + ((statements == null) ? 0 : statements.hashCode());
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
        SwitchBlockStatementGroup other = (SwitchBlockStatementGroup) obj;
        if (defaultPresent != other.defaultPresent) {
            return false;
        }
        if (labels == null) {
            if (other.labels != null) {
                return false;
            }
        } else if (!labels.equals(other.labels)) {
            return false;
        }
        if (statements == null) {
            if (other.statements != null) {
                return false;
            }
        } else if (!statements.equals(other.statements)) {
            return false;
        }
        return true;
    }
}
