package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.6")
@SuppressWarnings("serial")
public class EmptyStatement extends Statement implements StatementWithoutTrailingSubstatement {

    @Override
    public int hashCode() {
        return -31 * super.hashCode();
    }
    
    @Override
    public boolean equals(Object paramObject) {
        return paramObject != null && paramObject.getClass() == EmptyStatement.class &&
                super.equals(paramObject);
    }
}
