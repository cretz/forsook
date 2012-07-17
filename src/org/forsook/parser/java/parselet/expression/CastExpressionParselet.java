package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.UnaryExpression;
import org.forsook.parser.java.ast.expression.UnaryNotPlusMinusExpression;
import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("15.16")
@ParseletDefinition(
        name = "forsook.java.castExpression",
        emits = CastExpression.class,
        needs = {
            PrimitiveType.class,
            ReferenceType.class,
            UnaryExpression.class,
            UnaryNotPlusMinusExpression.class
        }
)
public class CastExpressionParselet extends ExpressionParselet<CastExpression> {

    @Override
    public CastExpression parse(Parser parser) {
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), ')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //type
        Type type = (Type) parser.first(PrimitiveType.class, ReferenceType.class);
        if (type == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //expression
        Expression expression = (Expression) parser.next(type instanceof PrimitiveType ?
                UnaryExpression.class : UnaryNotPlusMinusExpression.class);
        if (expression == null) {
            return null;
        }
        return new CastExpression(type, expression);
    }
}
