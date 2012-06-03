package org.forsook.parser.java.ast.packag;

import java.util.List;

import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.TypeDeclaration;

@SuppressWarnings("serial")
public class CompilationUnit extends JavaModel {
    private PackageDeclaration packageDeclaration;
    private List<ImportDeclaration> imports;
    private List<TypeDeclaration> types;
    
    public CompilationUnit(PackageDeclaration packageDeclaration, 
            List<ImportDeclaration> imports, List<TypeDeclaration> types) {
        this.packageDeclaration = packageDeclaration;
        this.imports = imports;
        this.types = types;
    }
}
