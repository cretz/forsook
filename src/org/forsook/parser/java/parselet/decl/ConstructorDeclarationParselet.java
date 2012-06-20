package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.ExplicitConstructorInvocationStatement;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.Parameter;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.InnerBlockStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.TypeParameter;

@JlsReference("8.8")
@ParseletDefinition(
        name = "forsook.java.constructorDeclaration",
        emits = ConstructorDeclaration.class,
        needs = {
            Identifier.class,
            ExplicitConstructorInvocationStatement.class,
            InnerBlockStatement.class
        }
)
public class ConstructorDeclarationParselet extends BodyDeclarationParselet<ConstructorDeclaration> {

    @Override
    public ConstructorDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        List<Modifier> modifiers = new ArrayList<Modifier>();
        AtomicReference<JavadocComment> javadoc = new AtomicReference<JavadocComment>();
        if (!parseJavadocAndModifiers(parser, javadoc, annotations,
                modifiers, Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE)) {
            return null;
        }
        //type parameters
        List<TypeParameter> typeParameters = parseTypeParameters(parser);
        if (typeParameters == null) {
            return null;
        }
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
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //statements
        List<Statement> statements = new ArrayList<Statement>();
        //local class gets no javadoc IMO
        parseWhiteSpaceAndComments(parser);
        //explicit constructor?
        Statement stmt = parser.next(ExplicitConstructorInvocationStatement.class);
        if (stmt != null) {
            statements.add(stmt);
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        do {
            stmt = (Statement) parser.next(InnerBlockStatement.class);
            if (stmt == null) {
                break;
            }
            statements.add(stmt);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (true);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //brace
        if (!parser.peekPresentAndSkip('}')) {
            return null;
        }
        return new ConstructorDeclaration(javadoc.get(), annotations, modifiers, 
                typeParameters, name, parameters, throwsList, new BlockStatement(statements));
    }

}
