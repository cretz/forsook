package org.forsook.parser.java.parselet.type;

import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.PrimitiveType.Primitive;
import org.forsook.parser.java.ast.type.TypeArguments;
import org.forsook.parser.java.ast.type.TypeParameter;
import org.forsook.parser.java.parselet.ParseletTestBase;
import org.junit.Test;

public class TypeParseletTest extends ParseletTestBase {

    @Test
    public void testClassOrInterfaceType() {
        assertString("a.b.c", ClassOrInterfaceType.class);  
        assertString("java.util.Map<K, V>", ClassOrInterfaceType.class);  
        assertString("Pair<A, Pair<A, B>>", ClassOrInterfaceType.class); 
        assertString("Pair<?, ?>", ClassOrInterfaceType.class);
        assertString("List<? extends List<?>>", ClassOrInterfaceType.class);
        assertString("List<? super List<?>>", ClassOrInterfaceType.class);
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
    public void testArrayType() {
        //test recursion
        String string = "int";
        for (int i = 0; i < 20; i++) {
            string += "[]";
            assertString(string, ArrayType.class);
        }
    }
    
    @Test
    public void testTypeArguments() {
        assertString("<Foo, ? extends Bar>", TypeArguments.class);
        assertString("<? super Bar, Foo>", TypeArguments.class);
    }
    
    @Test
    public void testTypeParameters() {
        assertString("T extends Foo & Bar", TypeParameter.class);
    }
}
