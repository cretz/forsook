package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.EnumBody;
import org.forsook.parser.java.ast.decl.EnumConstantDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.InitializerDeclaration;
import org.forsook.parser.java.ast.decl.MethodDeclaration;

@JlsReference("8.1.6")
@ParseletDefinition(
        name = "forsook.java.enumBody",
        emits = EnumBody.class,
        needs = {
            EnumConstantDeclaration.class,
            AnnotationTypeDeclaration.class,
            FieldDeclaration.class, 
            MethodDeclaration.class,
            ClassOrInterfaceDeclaration.class, 
            EnumDeclaration.class,
            ConstructorDeclaration.class,
            InitializerDeclaration.class
        }
)
public class EnumBodyParselet extends TypeBodyParselet<EnumBody> {

    @Override
    @SuppressWarnings("unchecked")
    public EnumBody parse(Parser parser) {
        //brace
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), '}')) {
            return null;
        }
        List<EnumConstantDeclaration> constants = new ArrayList<EnumConstantDeclaration>();
        do {
            //don't want to grab spacing here that could gobble up javadoc
            EnumConstantDeclaration constant = parser.next(EnumConstantDeclaration.class);
            if (constant == null) {
                parseWhiteSpaceAndComments(parser);
                break;
            }
            constants.add(constant);
        } while (parser.peekPresentAndSkip(','));
        //semicolon?
        List<BodyDeclaration> members = new ArrayList<BodyDeclaration>();
        if (parser.peekPresentAndSkip(';')) {
            //we probably have a body here then
            members.addAll(parseTypeMembers(parser, false,
                    AnnotationTypeDeclaration.class,
                    FieldDeclaration.class, 
                    MethodDeclaration.class,
                    ClassOrInterfaceDeclaration.class, 
                    EnumDeclaration.class,
                    ConstructorDeclaration.class,
                    InitializerDeclaration.class
                ));
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //brace
        if (!parser.peekPresentAndSkip('}')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        return new EnumBody(constants, members);
    }

}
