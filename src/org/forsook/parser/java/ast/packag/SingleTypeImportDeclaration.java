package org.forsook.parser.java.ast.packag;

import org.forsook.parser.java.ast.name.QualifiedName;

@SuppressWarnings("serial")
public class SingleTypeImportDeclaration extends ImportDeclaration {

    private QualifiedName name;
    
    public SingleTypeImportDeclaration(QualifiedName name) {
        this.name = name;
    }
}
