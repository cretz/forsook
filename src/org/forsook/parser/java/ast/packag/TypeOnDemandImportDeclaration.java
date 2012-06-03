package org.forsook.parser.java.ast.packag;

import org.forsook.parser.java.ast.name.QualifiedName;

@SuppressWarnings("serial")
public class TypeOnDemandImportDeclaration extends ImportDeclaration {

    private QualifiedName name;
    
    public TypeOnDemandImportDeclaration(QualifiedName name) {
        this.name = name;
    }
}
