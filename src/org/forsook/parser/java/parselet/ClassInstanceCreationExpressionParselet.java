package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.BodyDeclaration;
import org.forsook.parser.java.ast.ClassInstanceCreationExpression;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.ConstructorDeclaration;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.InitializerDeclaration;
import org.forsook.parser.java.ast.TypeArguments;

@JlsReference("15.9")
@ParseletDefinition(
        name = "forsook.java.classInstanceCreationExpression",
        emits = ClassInstanceCreationExpression.class
)
public class ClassInstanceCreationExpressionParselet 
        extends ExpressionParselet<ClassInstanceCreationExpression> {

    @Override
    public ClassInstanceCreationExpression parse(Parser parser) {
        //starts with primary expression?
        Expression scope = parsePrimaryExpression(parser, true);
        if (scope != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //dot
            if (!parser.peekPresentAndSkip('.')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //new
        if (!parser.peekPresentAndSkip("new")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type arguments
        TypeArguments typeArguments = parser.next(TypeArguments.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type
        ClassOrInterfaceType type = parser.next(ClassOrInterfaceType.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //diamond?
        boolean diamond = false;
        if (parser.peekPresentAndSkip('<')) {
            diamond = true;
            //spacing
            parseWhiteSpaceAndComments(parser);
            //end
            if (!parser.peekPresentAndSkip('>')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
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
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip(','));
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //brace
        List<BodyDeclaration> anonymousDeclarations = null;
        if (parser.peekPresentAndSkip('{')) {
            //don't take spacing here...needed for javadoc in decls below
            //body
            anonymousDeclarations = (List<BodyDeclaration>) parser.any(
                    ClassOrInterfaceDeclaration.class,
                    InitializerDeclaration.class,
                    ConstructorDeclaration.class,
                    FieldDeclaration.class,
                    MethodDeclaration.class);
            //spacing
            parseWhiteSpaceAndComments(parser);
            //brace
            if (!parser.peekPresentAndSkip('}')) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        return new ClassInstanceCreationExpression(scope, typeArguments, type, 
                diamond, arguments, anonymousDeclarations);
    }
}
