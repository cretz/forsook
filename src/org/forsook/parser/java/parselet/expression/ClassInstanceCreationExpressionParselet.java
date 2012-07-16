package org.forsook.parser.java.parselet.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.expression.ClassInstanceCreationExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.type.TypeArguments;

@JlsReference("15.9")
@ParseletDefinition(
        name = "forsook.java.classInstanceCreationExpression",
        emits = ClassInstanceCreationExpression.class,
        needs = {
            PrimaryExpression.class,
            TypeArguments.class,
            QualifiedName.class,
            Identifier.class,
            Expression.class,
            ClassOrInterfaceBody.class
        },
        recursiveMinimumSize = 6
)
public class ClassInstanceCreationExpressionParselet 
        extends ExpressionParselet<ClassInstanceCreationExpression> {

    @Override
    public ClassInstanceCreationExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLookAhead("new")) {
            return null;
        }
        //starts with primary expression?
        Expression scope = (Expression) parser.next(PrimaryExpression.class, 
                ClassInstanceCreationExpression.class);
        if (scope != null) {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //dot
            if (!parser.peekPresentAndSkip('.')) {
                //was the scope myself?
                if (scope instanceof ClassInstanceCreationExpression) {
                    parser.popLookAhead();
                    return (ClassInstanceCreationExpression) scope;
                } else {
                    return null;
                }
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //new
        if (!parser.peekPresentAndSkip("new")) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushLookAhead('(')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //pre type arguments
        TypeArguments preTypeArguments = parser.next(TypeArguments.class);
        //spacing
        parseWhiteSpaceAndComments(parser);
        //name
        QualifiedName name = null;
        if (scope == null) {
            name = parser.next(QualifiedName.class);
            if (name == null) {
                return null;
            }
        } else {
            Identifier identifier = parser.next(Identifier.class);
            if (identifier == null) {
                return null;
            }
            //make mutable list
            name = new QualifiedName(new ArrayList<Identifier>(Arrays.asList(identifier)));
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type arguments or diamond
        TypeArguments postTypeArguments = parser.next(TypeArguments.class);
        boolean diamond = false;
        if (postTypeArguments == null) {
            //diamond?
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
        } else {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //lookahead
        if (!parser.pushLookAhead(')')) {
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
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //body
        ClassOrInterfaceBody body = parser.next(ClassOrInterfaceBody.class);
        return new ClassInstanceCreationExpression(scope, preTypeArguments, name, 
                postTypeArguments, diamond, arguments, body);
    }
}
