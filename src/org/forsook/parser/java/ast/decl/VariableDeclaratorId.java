package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("8.3")
@SuppressWarnings("serial")
public class VariableDeclaratorId extends JavaModel {

    private Identifier name;
    private int arrayCount;
    
    public VariableDeclaratorId() {
    }
    
    public VariableDeclaratorId(Identifier name, int arrayCount) {
        this.name = name;
        this.arrayCount = arrayCount;
    }
    
    public Identifier getName() {
        return name;
    }
    
    public void setName(Identifier name) {
        this.name = name;
    }
    
    public int getArrayCount() {
        return arrayCount;
    }
    
    public void setArrayCount(int arrayCount) {
        this.arrayCount = arrayCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + arrayCount;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        VariableDeclaratorId other = (VariableDeclaratorId) obj;
        if (arrayCount != other.arrayCount) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
