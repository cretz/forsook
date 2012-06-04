package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference({ "8.1.6", "9.1.4" })
@SuppressWarnings("serial")
public class ClassOrInterfaceBody extends TypeBody {

    public ClassOrInterfaceBody() {
    }
    
    public ClassOrInterfaceBody(List<BodyDeclaration> members) {
        super(members);
    }
}
