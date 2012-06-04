package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("9.6")
@SuppressWarnings("serial")
public class AnnotationTypeDeclaration extends TypeDeclaration {

    public AnnotationTypeDeclaration() {
    }
    
    public AnnotationTypeDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations,
            Identifier name, Modifier modifiers, List<BodyDeclaration> members) {
        super(javadoc, annotations, name, modifiers, members);
    }
}
