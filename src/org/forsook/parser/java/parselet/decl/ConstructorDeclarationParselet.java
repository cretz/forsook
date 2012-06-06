package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.ExplicitConstructorInvocationStatement;
import org.forsook.parser.java.ast.decl.Parameter;
import org.forsook.parser.java.ast.decl.TypeDeclarationStatement;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;
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
            ClassOrInterfaceDeclaration.class,
            LocalVariableDeclarationExpression.class,
            Statement.class
        }
)
public class ConstructorDeclarationParselet extends BodyDeclarationParselet<ConstructorDeclaration> {

    @Override
    public ConstructorDeclaration parse(Parser parser) {
        //javadoc and annotations
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        JavadocComment javadoc = parseJavadocAndAnnotations(parser, annotations);
        //modifiers
        Modifier modifiers = parseModifiers(parser);
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
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
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
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //statements
        List<Statement> statements = new ArrayList<Statement>();
        //local class gets no javadoc IMO
        parseWhiteSpaceAndComments(parser);
        do {
            Statement stmt = parser.next(ExplicitConstructorInvocationStatement.class);
            if (stmt == null) {
                //local class?
                ClassOrInterfaceDeclaration decl = parser.next(ClassOrInterfaceDeclaration.class);
                if (decl != null && !decl.isInterface()) {
                    stmt = new TypeDeclarationStatement(decl);
                } else {
                    //variable declaration?
                    LocalVariableDeclarationExpression expr = 
                            parser.next(LocalVariableDeclarationExpression.class);
                    if (expr != null) {
                        stmt = new ExpressionStatement(expr);
                    } else {
                        //regular statement
                        stmt = parser.next(Statement.class);
                        if (stmt == null) {
                            return null;
                        }
                    }
                }
            }
            statements.add(stmt);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (!parser.peekPresentAndSkip('}'));
        return new ConstructorDeclaration(javadoc, annotations, modifiers, 
                typeParameters, name, parameters, throwsList, new BlockStatement(statements));
    }

}
