package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.ImportDeclaration;
import org.forsook.parser.java.ast.QualifiedName;

@JlsReference("7.5")
@ParseletDefinition(
        name = "forsook.java.importDeclaration",
        emits = ImportDeclaration.class,
        needs = QualifiedName.class
)
public class ImportDeclarationParselet extends JavaParselet<ImportDeclaration> {

    @Override
    public ImportDeclaration parse(Parser parser) {
        //needs "import" to be present
        if (!parser.peekPresentAndSkip("import")) {
            return null;
        }
        //whitespace required here
        if (parseWhiteSpaceAndComments(parser).isEmpty()) {
            return null;
        }
        //static present?
        boolean _static = parser.peekPresentAndSkip("static");
        //more whitespace?
        if (_static) {
            //required
            if (parseWhiteSpaceAndComments(parser).isEmpty()) {
                return null;
            }
        }
        //get name
        QualifiedName name = parser.next(QualifiedName.class);
        if (name == null) {
            return null;
        }
        boolean asterisk = false;
        if (name.getName().endsWith(".")) {
            parseWhiteSpaceAndComments(parser);
            if (!parser.peekPresentAndSkip('*')) {
                return null;
            }
            asterisk = true;
        }
        //return
        return new ImportDeclaration(name, _static, asterisk);
    }

}
