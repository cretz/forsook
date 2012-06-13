package org.forsook.parser.java.parselet.packag;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.ast.packag.ImportDeclaration;
import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.ast.packag.TypeDeclaration;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("7.3")
@ParseletDefinition(
        name = "forsook.java.compilationUnit",
        emits = CompilationUnit.class,
        needs = {
            PackageDeclaration.class,
            ImportDeclaration.class,
            ClassOrInterfaceDeclaration.class,
            AnnotationTypeDeclaration.class,
            EnumDeclaration.class
        }
)
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
        List<TypeDeclaration<?>> types = new ArrayList<TypeDeclaration<?>>();
        do {
            //no spacing here so declarations can get javadoc
            //type
            TypeDeclaration<?> type = (TypeDeclaration<?>) parser.first(
                    ClassOrInterfaceDeclaration.class,
                    AnnotationTypeDeclaration.class, EnumDeclaration.class);
            if (type == null) {
                //spacing 
                parseWhiteSpaceAndComments(parser);
                //semicolon
                if (!parser.peekPresentAndSkip(';')) {
                    break;
                }
            } else {
                types.add(type);
            }
        } while (true);
        //spacing
        parseWhiteSpaceAndComments(parser);
        return new CompilationUnit(packageDeclaration, imports, types);
    }

}
