package org.forsook.parser.java.ast;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.14.1")
@SuppressWarnings("serial")
public class BasicForStatement extends ForStatement {

    private List<Expression> init;
    private Expression compare;
    private List<Expression> update;
    
    public BasicForStatement() {
    }
    
    public BasicForStatement(List<Expression> init, Expression compare,
            List<Expression> update, Statement body) {
        super(body);
        this.init = init;
        this.compare = compare;
        this.update = update;
    }
    
    public List<Expression> getInit() {
        return init;
    }
    
    public void setInit(List<Expression> init) {
        this.init = init;
    }
    
    public Expression getCompare() {
        return compare;
    }
    
    public void setCompare(Expression compare) {
        this.compare = compare;
    }
    
    public List<Expression> getUpdate() {
        return update;
    }
    
    public void setUpdate(List<Expression> update) {
        this.update = update;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((compare == null) ? 0 : compare.hashCode());
        result = prime * result + ((init == null) ? 0 : init.hashCode());
        result = prime * result + ((update == null) ? 0 : update.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicForStatement other = (BasicForStatement) obj;
        if (compare == null) {
            if (other.compare != null) {
                return false;
            }
        } else if (!compare.equals(other.compare)) {
            return false;
        }
        if (init == null) {
            if (other.init != null) {
                return false;
            }
        } else if (!init.equals(other.init)) {
            return false;
        }
        if (update == null) {
            if (other.update != null) {
                return false;
            }
        } else if (!update.equals(other.update)) {
            return false;
        }
        return true;
    }
}
