package org.forsook.parser.java.ast.packag;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("7.5.4")
@SuppressWarnings("serial")
public class StaticOnDemandImportDeclaration extends ImportDeclaration {

    private QualifiedName name;
    
    public StaticOnDemandImportDeclaration() {
    }
    
    public StaticOnDemandImportDeclaration(QualifiedName name) {
        this.name = name;
    }
    
    public QualifiedName getName() {
        return name;
    }
    
    public void setName(QualifiedName name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        StaticOnDemandImportDeclaration other = (StaticOnDemandImportDeclaration) obj;
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
