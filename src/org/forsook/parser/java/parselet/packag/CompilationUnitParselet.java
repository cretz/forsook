package org.forsook.parser.java.parselet.packag;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.decl.TypeDeclaration;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.ast.packag.ImportDeclaration;
import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.parselet.JavaParselet;

public class CompilationUnitParselet extends JavaParselet<CompilationUnit> {

    @Override
    public CompilationUnit parse(Parser parser) {
        //package
        PackageDeclaration packageDeclaration = parser.next(PackageDeclaration.class);
        //imports
        List<ImportDeclaration> imports = new ArrayList<ImportDeclaration>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //import
            ImportDeclaration mport = parser.next(ImportDeclaration.class);
            if (mport == null) {
                break;
            }
        } while (true);
        //types
        List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //type
            TypeDeclaration type = parser.next(TypeDeclaration.class);
            if (type == null) {
                break;
            }
        } while (true);
        return new CompilationUnit(packageDeclaration, imports, types);
    }

}
