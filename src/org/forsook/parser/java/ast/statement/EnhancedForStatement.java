package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.14.2")
@SuppressWarnings("serial")
public class EnhancedForStatement extends ForStatement {

    private LocalVariableDeclarationExpression var;
    private Expression iterable;
    
    public EnhancedForStatement() {
    }
    
    public EnhancedForStatement(LocalVariableDeclarationExpression var,
            Expression iterable, Statement body) {
        super(body);
        this.var = var;
        this.iterable = iterable;
    }
    
    public LocalVariableDeclarationExpression getVar() {
        return var;
    }
    
    public void setVar(LocalVariableDeclarationExpression var) {
        this.var = var;
    }
    
    public Expression getIterable() {
        return iterable;
    }
    
    public void setIterable(Expression iterable) {
        this.iterable = iterable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((iterable == null) ? 0 : iterable.hashCode());
        result = prime * result + ((var == null) ? 0 : var.hashCode());
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
        EnhancedForStatement other = (EnhancedForStatement) obj;
        if (iterable == null) {
            if (other.iterable != null) {
                return false;
            }
        } else if (!iterable.equals(other.iterable)) {
            return false;
        }
        if (var == null) {
            if (other.var != null) {
                return false;
            }
        } else if (!var.equals(other.var)) {
            return false;
        }
        return true;
    }
}
