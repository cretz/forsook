package org.forsook.parser.java.parselet.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.NonWildTypeArguments;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.expression.ScopedExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.12")
@ParseletDefinition(
        name = "forsook.java.methodInvocationExpressionParselet",
        emits = MethodInvocationExpression.class,
        needs = {
            PrimaryExpression.class,
            QualifiedName.class,
            NonWildTypeArguments.class,
            Identifier.class,
            Expression.class
        },
        recursiveMinimumSize = 2
)
public class MethodInvocationExpressionParselet
        extends ExpressionParselet<MethodInvocationExpression> {

    @Override
    public MethodInvocationExpression parse(Parser parser) {
        //lookahead
        if (!parser.pushLastDepthLookAhead(parser.getAstDepth() + 1, '(')) {
            return null;
        }
        //scope
        //this is too heavy to allow left recursion here, turned to right recursion later...
        Expression scope = (Expression) parser.next(PrimaryExpression.class, 
                MethodInvocationExpression.class);
        QualifiedName className = null;
        boolean superPresent = false;
        QualifiedName methodName = null;
        if (scope == null) {
            //class name
            className = parser.next(QualifiedName.class);
            if (className != null) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (parser.peekPresentAndSkip('.')) {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                } else  {
                    //method name
                    methodName = className;
                    className = null;
                }
            }
            //super
            superPresent = parser.peekPresentAndSkip("super");
            if (superPresent) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (!parser.peekPresentAndSkip('.')) {
                    return null;
                }
                //spacing
                parseWhiteSpaceAndComments(parser);
            }
        } else {
            //wait a minute, did the scope think it was field access?
            if (scope instanceof FieldAccessExpression) {
                //we're gonna pretend it's the scope w/ the method name
                //  (I hope I don't pay for this)
                methodName = new QualifiedName(new ArrayList<Identifier>(
                        Arrays.asList(((FieldAccessExpression) scope).getField())));
                className = ((FieldAccessExpression) scope).getClassName();
                scope = ((FieldAccessExpression) scope).getScope();
            } else {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (!parser.peekPresentAndSkip('.')) {
                    return null;
                }
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //type arguments
        NonWildTypeArguments typeArguments = null;
        if (methodName == null) {
            typeArguments = parser.next(NonWildTypeArguments.class);
            if (!superPresent && scope == null && typeArguments == null) {
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
            //method name
            Identifier methodIdentifier = parser.next(Identifier.class);
            if (methodIdentifier == null) {
                return null;
            }
            //needs to be mutable list
            methodName = new QualifiedName(
                    new ArrayList<Identifier>(Arrays.asList(methodIdentifier)));
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
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ')')) {
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
        //regular
        MethodInvocationExpression expr = new MethodInvocationExpression(scope, className, 
                superPresent, typeArguments, methodName, arguments);
        ArrayAccessExpression pendingArray = null;
        //now, right recursively get other method calls :-(
        int lastKnownGoodCursor = parser.getCursor();
        do {
            boolean found = false;
            //dot or array access?
            if (parser.peekPresentAndSkip('.')) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //method?
                MethodInvocationExpression parent = parser.next(MethodInvocationExpression.class);
                if (parent != null) {
                    //set parent scope
                    ScopedExpression scoped = parent;
                    while (scoped.getScope() != null) {
                        //better be a scoped expression or somethin's messed up
                        scoped = (ScopedExpression) scoped.getScope();
                    }
                    scoped.setScope(pendingArray != null ? pendingArray : expr);
                    pendingArray = null;
                    expr = parent;
                    found = true;
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                    lastKnownGoodCursor = parser.getCursor();
                }
            } else if (parser.peekPresentAndSkip('[')) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //lookahead
                if (parser.pushLookAhead(']')) {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                    //expression
                    Expression index = parser.next(Expression.class);
                    if (index == null) {
                        return null;
                    }
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                    //bracket
                    if (!parser.peekPresentAndSkip(']')) {
                        return null;
                    }
                    //pop lookahead
                    parser.popLookAhead();
                    //populate
                    pendingArray = new ArrayAccessExpression(expr, index);
                    expr = null;
                    found = true;
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                }
            }
            if (!found) {
                break;
            }
        } while (true);
        //rollback cursor
        int finalCursor = parser.getCursor();
        for (int i = 0; i < finalCursor - lastKnownGoodCursor; i++) {
            //TODO: expose forced setter?
            parser.backupCursor();
        }
        return expr;
    }
}
