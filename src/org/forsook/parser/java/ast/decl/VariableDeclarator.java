package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.JavaModel;

@JlsReference("8.3")
@SuppressWarnings("serial")
public class VariableDeclarator extends JavaModel {
    
    private VariableDeclaratorId id;
    private Expression init;
    
    public VariableDeclarator() {
    }
    
    public VariableDeclarator(VariableDeclaratorId id, Expression init) {
        this.id = id;
        this.init = init;
    }
    
    public VariableDeclaratorId getId() {
        return id;
    }
    
    public void setId(VariableDeclaratorId id) {
        this.id = id;
    }
    
    public Expression getInit() {
        return init;
    }
    
    public void setInit(Expression init) {
        this.init = init;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((init == null) ? 0 : init.hashCode());
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
        VariableDeclarator other = (VariableDeclarator) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (init == null) {
            if (other.init != null) {
                return false;
            }
        } else if (!init.equals(other.init)) {
            return false;
        }
        return true;
    }
}
