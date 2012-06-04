package org.forsook.parser.java.parselet.packag;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("7.4")
@ParseletDefinition(
        name = "forsook.java.packageDeclaration",
        emits = PackageDeclaration.class,
        needs = {
            AnnotationExpression.class,
            QualifiedName.class
        }
)
public class PackageDeclarationParselet extends JavaParselet<PackageDeclaration> {

    @Override
    public PackageDeclaration parse(Parser parser) {
        //annotations
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        do {
            AnnotationExpression annotation = parser.next(AnnotationExpression.class);
            if (annotation == null) {
                break;
            }
            annotations.add(annotation);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (true);
        //needs "package" to be present
        if (!parser.peekPresentAndSkip("package")) {
            return null;
        }
        //must have something here
        if (parseWhiteSpaceAndComments(parser).isEmpty()) {
            return null;
        }
        //get name
        QualifiedName name = parser.next(QualifiedName.class);
        if (name == null) {
            return null;
        }
        //could have more mess
        parseWhiteSpaceAndComments(parser);
        //requires semicolon, then we're good
        if (!parser.peekPresentAndSkip(';')) {
            return null;
        }
        //return
        return new PackageDeclaration(annotations, name);
    }

}
