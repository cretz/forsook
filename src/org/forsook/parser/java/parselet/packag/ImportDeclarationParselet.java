package org.forsook.parser.java.parselet.packag;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.packag.ImportDeclaration;
import org.forsook.parser.java.ast.packag.SingleStaticImportDeclaration;
import org.forsook.parser.java.ast.packag.SingleTypeImportDeclaration;
import org.forsook.parser.java.ast.packag.StaticOnDemandImportDeclaration;
import org.forsook.parser.java.ast.packag.TypeOnDemandImportDeclaration;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("7.5")
@ParseletDefinition(
        name = "forsook.java.importDeclaration",
        emits = ImportDeclaration.class,
        needs = {
            SingleTypeImportDeclaration.class,
            TypeOnDemandImportDeclaration.class,
            SingleStaticImportDeclaration.class,
            StaticOnDemandImportDeclaration.class
        }
)
public class ImportDeclarationParselet<T extends ImportDeclaration> extends JavaParselet<T> {

    @Override
    @SuppressWarnings("unchecked")
    public T parse(Parser parser) {
        return (T) parser.first(SingleTypeImportDeclaration.class,
                TypeOnDemandImportDeclaration.class,
                SingleStaticImportDeclaration.class,
                StaticOnDemandImportDeclaration.class);
    }

}
