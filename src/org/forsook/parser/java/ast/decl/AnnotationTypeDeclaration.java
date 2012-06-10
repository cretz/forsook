package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.packag.TypeDeclaration;

@JlsReference("9.6")
@SuppressWarnings("serial")
public class AnnotationTypeDeclaration extends TypeDeclaration<AnnotationTypeBody> {

    public AnnotationTypeDeclaration() {
    }
    
    public AnnotationTypeDeclaration(JavadocComment javadoc, List<AnnotationExpression> annotations,
            Identifier name, List<Modifier> modifiers, AnnotationTypeBody members) {
        super(javadoc, annotations, name, modifiers, members);
    }
}
