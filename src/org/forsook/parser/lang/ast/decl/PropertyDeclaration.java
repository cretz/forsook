package org.forsook.parser.lang.ast.decl;

import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.lang.ast.ForsookModel;

@SuppressWarnings("serial")
public class PropertyDeclaration extends FieldDeclaration implements ForsookModel {

    private Modifier getterModifier;
    private Modifier setterModifier;
    
}
