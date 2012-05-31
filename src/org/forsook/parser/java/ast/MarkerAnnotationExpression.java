package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("9.7.2")
@SuppressWarnings("serial")
public class MarkerAnnotationExpression extends AnnotationExpression {

    public MarkerAnnotationExpression() {
    }
    
    public MarkerAnnotationExpression(QualifiedName name) {
        super(name);
    }
}
