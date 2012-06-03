package org.forsook.parser.java.ast.packag;

import org.forsook.parser.java.ast.name.QualifiedName;

@SuppressWarnings("serial")
public class StaticOnDemandImportDeclaration extends ImportDeclaration {

    private QualifiedName name;
    
    public StaticOnDemandImportDeclaration(QualifiedName name) {
        this.name = name;
    }
}
