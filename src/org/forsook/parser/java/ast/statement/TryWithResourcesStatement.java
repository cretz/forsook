package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.20.3")
@SuppressWarnings("serial")
public class TryWithResourcesStatement extends TryStatement {

    private List<Resource> resources;
    
    public TryWithResourcesStatement() {
    }
    
    public TryWithResourcesStatement(BlockStatement tryBlock, List<CatchClause> catchClauses, 
            BlockStatement finallyBlock, List<Resource> resources) {
        super(tryBlock, catchClauses, finallyBlock);
        this.resources = resources;
    }
    
    public List<Resource> getResources() {
        return resources;
    }
    
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((resources == null) ? 0 : resources.hashCode());
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
        TryWithResourcesStatement other = (TryWithResourcesStatement) obj;
        if (resources == null) {
            if (other.resources != null) {
                return false;
            }
        } else if (!resources.equals(other.resources)) {
            return false;
        }
        return true;
    }
}
