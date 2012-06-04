package org.forsook.parser.java.parselet.decl;

import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationTypeBody;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.AnnotationTypeElementDeclaration;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.decl.FieldDeclaration;

@JlsReference("9.6")
@ParseletDefinition(
        name = "forsook.java.annotationTypeBody",
        emits = AnnotationTypeBody.class,
        needs = {
            AnnotationTypeDeclaration.class,
            AnnotationTypeElementDeclaration.class,
            FieldDeclaration.class, 
            ClassOrInterfaceDeclaration.class, 
            EnumDeclaration.class
        }
)
public class AnnotationTypeBodyParselet extends TypeBodyParselet<AnnotationTypeBody> {

    @Override
    @SuppressWarnings("unchecked")
    public AnnotationTypeBody parse(Parser parser) {
        List<BodyDeclaration> members = parseTypeMembers(parser, true,
                AnnotationTypeDeclaration.class,
                AnnotationTypeElementDeclaration.class,
                FieldDeclaration.class, 
                ClassOrInterfaceDeclaration.class, 
                EnumDeclaration.class);
        if (members == null) {
            return null;
        }
        return new AnnotationTypeBody(members);
    }

}
