package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.BlockStatement;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.MethodDeclaration;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.Parameter;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.ReferenceType;
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
        //javadoc and annotations
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifier = parseModifiers(parser);
        //type parameters
        List<TypeParameter> typeParameters = parseTypeParameters(parser);
        //result
        Type result = (Type) parser.first(ReferenceType.class, VoidType.class);
        if (result == null) {
            return null;
        }
        //name
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
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
        return new MethodDeclaration(javadoc, annotations, modifier, typeParameters, 
                result, name, parameters, throwsList, block);
    }

}
