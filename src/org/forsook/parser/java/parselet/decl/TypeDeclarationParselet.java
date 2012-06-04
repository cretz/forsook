package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.InitializerDeclaration;
import org.forsook.parser.java.ast.decl.MethodDeclaration;
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
public abstract class TypeDeclarationParselet<T extends TypeDeclaration> 
        extends BodyDeclarationParselet<T> {
    
    @SuppressWarnings("unchecked")
    protected List<BodyDeclaration> parseTypeMembers(Parser parser) {
        List<BodyDeclaration> members = new ArrayList<BodyDeclaration>();
        do {
            //no whitespace checking here because it'll gobble up javadoc
            List<BodyDeclaration> memberList = (List<BodyDeclaration>) parser.any(
                    FieldDeclaration.class, MethodDeclaration.class,
                    ClassOrInterfaceDeclaration.class, InitializerDeclaration.class,
                    ConstructorDeclaration.class);
            if (memberList.isEmpty()) {
                //could just be a semicolon hanging out there (we'll ignore it for now)
                //spacing
                parseWhiteSpaceAndComments(parser);
                if (!parser.peekPresentAndSkip(';')) {
                    break;
                }
            } else {
                members.addAll(memberList);
            }
        } while (true);
        return members;
    }
    
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
