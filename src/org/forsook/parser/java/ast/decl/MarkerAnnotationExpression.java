package org.forsook.parser.java.ast.decl;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7.2")
@SuppressWarnings("serial")
public class MarkerAnnotationExpression extends AnnotationExpression {

    public MarkerAnnotationExpression() {
    }
    
    public MarkerAnnotationExpression(QualifiedName name) {
        super(name);
    }
}
