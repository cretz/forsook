package org.forsook.parser.java.ast.packag;

import org.forsook.parser.java.ast.name.QualifiedName;

@SuppressWarnings("serial")
public class SingleStaticImportDeclaration extends ImportDeclaration {

    private QualifiedName name;
    
    public SingleStaticImportDeclaration(QualifiedName name) {
        this.name = name;
    }
}
