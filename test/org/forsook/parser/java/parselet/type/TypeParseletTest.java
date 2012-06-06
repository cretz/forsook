package org.forsook.parser.java.parselet.type;

import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.PrimitiveType.Primitive;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Ignore;
import org.junit.Test;

public class TypeParseletTest extends ParseletTestBase {

    @Test
    @Ignore("TODO")
    public void testClassOrInterfaceType() {
        //"a.b.c"  
        //"java.util.Map<K, V>"  
        //"Pair<A, Pair<A, /*comment*/ B>>" 
        //"Pair<?, ?>"
        //"List<? extends List<?>>"
        //"List<? super List<?>>"
        //null - "List<? extends ?>"
    }
    
    @Test
    public void testPrimitiveType() {
        for (Primitive primitive : Primitive.values()) {
            assertParse(primitive.name().toLowerCase(), new PrimitiveType(primitive));
            assertNull(primitive.name().toLowerCase() + "1", PrimitiveType.class);
            assertNull(primitive.name(), PrimitiveType.class);
            assertNull("_" + primitive.name(), PrimitiveType.class);
        }
    }
    
    @Test
    @Ignore("TODO")
    public void testReferenceType() {
        
    }
}
