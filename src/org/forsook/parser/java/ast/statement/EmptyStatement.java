package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.6")
@SuppressWarnings("serial")
public class EmptyStatement extends Statement {

    @Override
    public int hashCode() {
        //TODO what to do here?
        return -31;
    }
    
    @Override
    public boolean equals(Object paramObject) {
        return paramObject != null && paramObject.getClass() == EmptyStatement.class;
    }
}
