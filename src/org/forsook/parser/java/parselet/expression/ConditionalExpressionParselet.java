package org.forsook.parser.java.parselet.expression;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.expression.ConditionalExpression;

@ParseletDefinition(
        name = "forsook.java.conditionalExpression",
        emits = ConditionalExpression.class
)
public class ConditionalExpressionParselet extends ExpressionParselet<ConditionalExpression> {

    @Override
    public ConditionalExpression parse(Parser parser) {
        //first, a conditional or expression
        return null;
    }

//TODO
//    private ConditionalExpression parseConditionalOrExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseConditionalAndExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseInclusiveOrExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseExclusiveOrExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseAndExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseEqualityExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseRelationalExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseShiftExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseAdditiveExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseMultiplicativeExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseUnaryExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parseCastExpression(Parser parser) {
//        
//    }
//    
//    private ConditionalExpression parsePostfixExpression(Parser parser) {
//        
//    }
}
