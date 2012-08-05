package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("15.11")
@ParseletDefinition(
        name = "forsook.java.fieldAccessExpression",
        emits = FieldAccessExpression.class,
        needs = {
            PrimaryExpression.class,
            QualifiedName.class,
            Identifier.class
        },
        recursiveMinimumSize = 2
)
public class FieldAccessExpressionParselet extends ExpressionParselet<FieldAccessExpression> {
    
    @Override
    public FieldAccessExpression parse(Parser parser) {
        //can't call myself directly
        for (int i = parser.getParseletStack().size() - 2; i >= 0; i--) {
            if (parser.getParseletStack().get(i).getParselet().getClass() ==
                    FieldAccessExpressionParselet.class) {
                return null;
            } else if (parser.getParseletStack().get(i).getParselet().getClass() != 
                    PrimaryExpressionParselet.class &&
                    parser.getParseletStack().get(i).getParselet().getClass() !=
                    PrimaryNoNewArrayExpressionParselet.class) {
                break;
            }
        }
        //lookahead
        if (!parser.pushLookAhead('.')) {
            return null;
        }
        //scope
        Expression scope = (Expression) parser.next(PrimaryExpression.class);
        //pop here
        parser.popLookAhead();
        //class name
        QualifiedName className = null;
        boolean superPresent = false;
        Identifier field = null;
        if (scope == null) {
            //just super?
            if (parser.peekPresentAndSkip("super")) {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot
                if (!parser.peekPresentAndSkip('.')) {
                    return null;
                }
            } else {
                //nope, better be a class name
                if (!parser.pushLookAhead('.')) {
                    return null;
                }
                className = parser.next(QualifiedName.class);
                if (className == null) {
                    return null;
                }
                parser.popLookAhead();
                //spacing
                parseWhiteSpaceAndComments(parser);
                //dot?
                if (parser.peekPresentAndSkip('.')) {
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                    //super?
                    if (parser.peekPresentAndSkip("super")) {
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
                    //hrmm...break up the class name
                    field = className.getIdentifiers().get(
                            className.getIdentifiers().size() - 1);
                    if (className.getIdentifiers().size() > 1) {
                        className = new QualifiedName(className.getIdentifiers().
                                subList(0, className.getIdentifiers().size() - 1));
                    } else {
                        className = null;
                    }
                }
            }
        } else {
            //spacing
            parseWhiteSpaceAndComments(parser);
            //dot
            if (!parser.peekPresentAndSkip('.')) {
                //could be in the scope
                if (scope instanceof FieldAccessExpression) {
                    return (FieldAccessExpression) scope;
                }
                return null;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        //dot (only required if field isn't already set)
        if (field == null) {
            //field
            field = parser.next(Identifier.class);
            if (field == null) {
                return null;
            }
        }
        FieldAccessExpression expr = new FieldAccessExpression(scope, className, superPresent, field);
        //lets loop
        ArrayAccessExpression pendingArray = null;
        int lastKnownGoodCursor = parser.getCursor();
        do {
            parseWhiteSpaceAndComments(parser);
            if (parser.peekPresentAndSkip('.')) {
                parseWhiteSpaceAndComments(parser);
                field = parser.next(Identifier.class);
                if (field == null) {
                    break;
                }
                if (pendingArray != null) {
                    expr = new FieldAccessExpression(pendingArray, null, false, field);
                } else {
                    expr = new FieldAccessExpression(expr, null, false, field);
                }
                lastKnownGoodCursor = parser.getCursor();
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
                    //spacing
                    parseWhiteSpaceAndComments(parser);
                }
            } else {
                break;
            }
        } while (true);
        //rollback cursor
        int finalCursor = parser.getCursor();
        for (int i = 0; i < finalCursor - lastKnownGoodCursor; i++) {
            //TODO: expose forced setter?
            parser.backupCursor();
        }
        //regular
        return expr;
    }
}
