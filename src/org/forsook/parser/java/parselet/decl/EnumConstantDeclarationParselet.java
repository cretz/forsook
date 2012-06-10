package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.decl.EnumConstantDeclaration;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;

@JlsReference("8.9.1")
@ParseletDefinition(
        name = "forsook.java.enumConstantDeclaration",
        emits = EnumConstantDeclaration.class,
        needs = {
            Identifier.class,
            Expression.class,
            ClassOrInterfaceBody.class
        }
)
public class EnumConstantDeclarationParselet 
        extends BodyDeclarationParselet<EnumConstantDeclaration> {

    @Override
    public EnumConstantDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        AtomicReference<JavadocComment> javadoc = new AtomicReference<JavadocComment>();
        if (!parseJavadocAndModifiers(parser, javadoc, annotations, null)) {
            return null;
        }
        //identifier
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //arguments
        List<Expression> arguments = new ArrayList<Expression>();
        do {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //argument
            Expression argument = parser.next(Expression.class);
            if (argument == null) {
                if (arguments.isEmpty()) {
                    break;
                } else  {
                    return null;
                }
            }
            arguments.add(argument);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //body
        ClassOrInterfaceBody body = parser.next(ClassOrInterfaceBody.class);
        return new EnumConstantDeclaration(javadoc.get(), annotations, name, arguments, body);
    }

}
