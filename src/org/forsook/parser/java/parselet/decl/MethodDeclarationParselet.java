package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.MethodDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.Parameter;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.TypeParameter;
import org.forsook.parser.java.ast.type.VoidType;

@JlsReference("8.4")
@ParseletDefinition(
        name = "forsook.java.methodDeclaration",
        emits = MethodDeclaration.class
)
public class MethodDeclarationParselet extends BodyDeclarationParselet<MethodDeclaration> {

    @Override
    public MethodDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        List<Modifier> modifiers = new ArrayList<Modifier>();
        AtomicReference<JavadocComment> javadoc = new AtomicReference<JavadocComment>();
        if (!parseJavadocAndModifiers(parser, javadoc, annotations,
                modifiers, Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE,
                Modifier.ABSTRACT, Modifier.STATIC, Modifier.FINAL, 
                Modifier.SYNCHRONIZED, Modifier.NATIVE, Modifier.STRICTFP)) {
            return null;
        }
        //type parameters
        List<TypeParameter> typeParameters = parseTypeParameters(parser);
        //result
        Type result = (Type) parser.first(Type.class, VoidType.class);
        if (result == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parameters
        List<Parameter> parameters = parseParameters(parser);
        if (parameters == null) {
            return null;
        }
        //throws
        List<ClassOrInterfaceType> throwsList = parseThrows(parser);
        if (throwsList == null) {
            return null;
        }
        //block
        BlockStatement block = null;
        if (!parser.peekPresentAndSkip(';')) {
            block = parser.next(BlockStatement.class);
            if (block == null) {
                return null;
            }
        }
        return new MethodDeclaration(javadoc.get(), annotations, modifiers, typeParameters, 
                result, name, parameters, throwsList, block);
    }

}
