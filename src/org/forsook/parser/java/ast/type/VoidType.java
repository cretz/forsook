package org.forsook.parser.java.ast.type;

import org.forsook.parser.java.JlsReference;

@JlsReference("8.4.5")
@SuppressWarnings("serial")
public class VoidType extends Type {

    @Override
    public int hashCode() {
        //TODO: is this ok?
        return -63;
    }
    
    @Override
    public boolean equals(Object paramObject) {
        return paramObject != null && paramObject.getClass() == VoidType.class;
    }
}
