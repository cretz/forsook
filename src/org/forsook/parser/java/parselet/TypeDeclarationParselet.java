package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.BodyDeclaration;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.InitializerDeclaration;
import org.forsook.parser.java.ast.TypeDeclaration;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;

@ParseletDefinition(
        needs = {
            Identifier.class,
            ClassOrInterfaceType.class,
            InitializerDeclaration.class,
            ClassOrInterfaceDeclaration.class
        }
)
public abstract class TypeDeclarationParselet<T extends TypeDeclaration> extends BodyDeclarationParselet<T> {
    
    protected List<BodyDeclaration> classOrInterfaceBodyDeclaration(Parser parser) {
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        List<BodyDeclaration> ret = new ArrayList<BodyDeclaration>();
        //spacing
        parseWhiteSpaceAndComments(parser);
        BodyDeclaration decl;
        do {
            //try different ones
            decl = (BodyDeclaration) parser.first(
                    InitializerDeclaration.class,
                    ClassOrInterfaceDeclaration.class//,
                    //TODO:
                    //EnumDeclaration.class,
                    //AnnotationTypeDeclaration.class,
                    //FieldDeclaration.class,
                    //MethodDeclaration.class
                    );
            if (decl != null) {
                ret.add(decl);
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (decl != null);
        return parser.peekPresentAndSkip('}') ? ret : null;
    }
}
