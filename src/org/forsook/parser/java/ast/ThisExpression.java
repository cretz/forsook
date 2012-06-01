package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference("15.8.3")
@SuppressWarnings("serial")
public class ThisExpression extends Expression {

    @Override
    public int hashCode() {
        //TODO: is this ok?
        return -13;
    }
    
    @Override
    public boolean equals(Object paramObject) {
        return paramObject != null && paramObject.getClass() == ThisExpression.class;
    }
}
