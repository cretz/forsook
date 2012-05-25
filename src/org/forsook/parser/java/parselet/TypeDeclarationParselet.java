package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.BodyDeclaration;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.TypeDeclaration;
import org.forsook.parser.java.ast.TypeParameter;

@ParseletDepends({
    IdentifierParselet.class,
    ClassOrInterfaceTypeParselet.class,
    InitializerDeclarationParselet.class,
    ClassOrInterfaceDeclarationParselet.class
})
public abstract class TypeDeclarationParselet<T extends TypeDeclaration> extends BodyDeclarationParselet<T> {

    protected List<TypeParameter> parseTypeParameters(Parser parser) {
        //spacing
        parseWhiteSpaceAndComments(parser);
        List<TypeParameter> ret = new ArrayList<TypeParameter>();
        //begin
        if (!parser.peekPresentAndSkip('<')) {
            return ret;
        }
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //name
            String name = parser.next(IdentifierParselet.class);
            if (name == null) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //type bound
            List<ClassOrInterfaceType> typeBound = new ArrayList<ClassOrInterfaceType>();
            if (parser.peekPresentAndSkip("extends")) {
                do {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                    //type
                    ClassOrInterfaceType type = parser.next(ClassOrInterfaceTypeParselet.class);
                    if (type == null) {
                        return null;
                    }
                    typeBound.add(type);
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                } while (parser.peekPresentAndSkip('&'));
            }
            //more spacing
            parseWhiteSpaceAndComments(parser);
            //add
            ret.add(new TypeParameter(name, typeBound));
        } while (parser.peekPresentAndSkip(','));
        //end
        if (!parser.peekPresentAndSkip('>')) {
            return null;
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
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
                    InitializerDeclarationParselet.class,
                    ClassOrInterfaceDeclarationParselet.class//,
                    //TODO:
                    //EnumDeclarationParselet.class,
                    //AnnotationTypeDeclarationParselet.class,
                    //FieldDeclarationParselet.class,
                    //MethodDeclarationParselet.class
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
