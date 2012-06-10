package org.forsook.parser.java.parselet.decl;

import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.InitializerDeclaration;
import org.forsook.parser.java.ast.decl.MethodDeclaration;

@JlsReference("8.1.6")
@ParseletDefinition(
        name = "forsook.java.classOrInterfaceBody",
        emits = ClassOrInterfaceBody.class,
        needs = {
            AnnotationTypeDeclaration.class,
            FieldDeclaration.class, 
            MethodDeclaration.class,
            ClassOrInterfaceDeclaration.class, 
            EnumDeclaration.class,
            ConstructorDeclaration.class,
            InitializerDeclaration.class
        }
)
public class ClassOrInterfaceBodyParselet extends TypeBodyParselet<ClassOrInterfaceBody> {

    @Override
    @SuppressWarnings("unchecked")
    public ClassOrInterfaceBody parse(Parser parser) {
        //members
        List<BodyDeclaration> members = parseTypeMembers(parser, true, 
                AnnotationTypeDeclaration.class,
                FieldDeclaration.class, 
                MethodDeclaration.class,
                ClassOrInterfaceDeclaration.class, 
                EnumDeclaration.class,
                ConstructorDeclaration.class,
                InitializerDeclaration.class
            );
        if (members == null) {
            return null;
        }
        return new ClassOrInterfaceBody(members);
    }

}
