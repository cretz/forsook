package org.forsook.parser.java.parselet.packag;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.packag.SingleTypeImportDeclaration;

public class SingleTypeImportDeclarationParselet 
        extends ImportDeclarationParselet<SingleTypeImportDeclaration> {

    @Override
    public SingleTypeImportDeclaration parse(Parser parser) {
        //needs "import" to be present
        if (!parser.peekPresentAndSkip("import")) {
            return null;
        }
        //whitespace required here
        if (parseWhiteSpaceAndComments(parser).isEmpty()) {
            return null;
        }
        //simple type name
        QualifiedName name = parser.next(QualifiedName.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //semicolon
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        return new SingleTypeImportDeclaration(name);
    }
}
