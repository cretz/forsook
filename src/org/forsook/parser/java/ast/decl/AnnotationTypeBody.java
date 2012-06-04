package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("9.6")
@SuppressWarnings("serial")
public class AnnotationTypeBody extends TypeBody {

    public AnnotationTypeBody() {
    }
    
    public AnnotationTypeBody(List<BodyDeclaration> members) {
        super(members);
    }
}
