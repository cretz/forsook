package org.forsook.parser.java.ast.packag;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;

@JlsReference("7.3")
@SuppressWarnings("serial")
public class CompilationUnit extends JavaModel {
    
    private PackageDeclaration packageDeclaration;
    private List<ImportDeclaration> imports;
    private List<TypeDeclaration<?>> types;
    
    public CompilationUnit() {
    }
    
    public CompilationUnit(PackageDeclaration packageDeclaration, 
            List<ImportDeclaration> imports, List<TypeDeclaration<?>> types) {
        this.packageDeclaration = packageDeclaration;
        this.imports = imports;
        this.types = types;
    }
    
    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }
    
    public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
        this.packageDeclaration = packageDeclaration;
    }
    
    public List<ImportDeclaration> getImports() {
        return imports;
    }
    
    public void setImports(List<ImportDeclaration> imports) {
        this.imports = imports;
    }
    
    public List<TypeDeclaration<?>> getTypes() {
        return types;
    }
    
    public void setTypes(List<TypeDeclaration<?>> types) {
        this.types = types;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imports == null) ? 0 : imports.hashCode());
        result = prime
                * result
                + ((packageDeclaration == null) ? 0 : packageDeclaration
                        .hashCode());
        result = prime * result + ((types == null) ? 0 : types.hashCode());
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
        CompilationUnit other = (CompilationUnit) obj;
        if (imports == null) {
            if (other.imports != null) {
                return false;
            }
        } else if (!imports.equals(other.imports)) {
            return false;
        }
        if (packageDeclaration == null) {
            if (other.packageDeclaration != null) {
                return false;
            }
        } else if (!packageDeclaration.equals(other.packageDeclaration)) {
            return false;
        }
        if (types == null) {
            if (other.types != null) {
                return false;
            }
        } else if (!types.equals(other.types)) {
            return false;
        }
        return true;
    }
}
