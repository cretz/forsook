package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.PackageDeclaration;
import org.forsook.parser.java.ast.QualifiedName;
import org.forsook.parser.java.ast.WhiteSpace;

@JlsReference("7.4")
@ParseletDefinition(
        name = "forsook.java.packageDeclaration",
        emits = PackageDeclaration.class,
        needs = {
            WhiteSpace.class,
            Comment.class,
            AnnotationExpression.class,
            QualifiedName.class
        }
)
public class PackageDeclarationParselet extends JavaParselet<PackageDeclaration> {

    @Override
    public PackageDeclaration parse(Parser parser) {
        //hold annotations
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        //loop over whitespace and annotations, only holding on to annotations
        for (Object found : parser.any(WhiteSpace.class, Comment.class,  AnnotationExpression.class)) {
            if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            }
        }
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
        //can't end w/ a dot
        if (name != null && name.getName().endsWith(".")) {
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
