package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.InitializerDeclaration;
import org.forsook.parser.java.ast.decl.TypeDeclaration;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;

@ParseletDefinition(
        needs = {
            Identifier.class,
            ClassOrInterfaceType.class,
            InitializerDeclaration.class,
            ClassOrInterfaceDeclaration.class
        }
)
public abstract class TypeDeclarationParselet<T extends TypeDeclaration<?>> 
        extends BodyDeclarationParselet<T> {
    
    protected List<ClassOrInterfaceType> parseExtendsOrImplementsList(String keyword, Parser parser) {
        List<ClassOrInterfaceType> list = new ArrayList<ClassOrInterfaceType>();
        if (parser.peekPresentAndSkip(keyword)) {
            ClassOrInterfaceType type;
            do {
                parseWhiteSpaceAndComments(parser);
                type = parser.next(ClassOrInterfaceType.class);
                if (type != null) {
                    list.add(type);
                    parseWhiteSpaceAndComments(parser);
                }
            } while (type != null && parser.peekPresentAndSkip(','));
            if (list.isEmpty()) {
                return null;
            }
        }
        return list;
    }
}
