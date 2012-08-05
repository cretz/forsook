package org.forsook.parser.lang.parselet.decl;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.lang.ForsookHandler;
import org.forsook.parser.lang.ast.decl.PropertyDeclaration;

@ParseletDefinition(
        name = "forsook.lang.propertyDeclaration",
        emits = { FieldDeclaration.class, PropertyDeclaration.class }
)
public class PropertyDeclarationHandler extends ForsookHandler<PropertyDeclaration> {

    @Override
    public PropertyDeclaration parse(Parser parser) {
        // TODO Auto-generated method stub
        return null;
    }

}
