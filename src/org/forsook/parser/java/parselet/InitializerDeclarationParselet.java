package org.forsook.parser.java.parselet;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.BlockStatement;
import org.forsook.parser.java.ast.InitializerDeclaration;

public class InitializerDeclarationParselet extends BodyDeclarationParselet<InitializerDeclaration> {

    @Override
    public InitializerDeclaration parse(Parser parser) {
        boolean _static = parser.peekPresentAndSkip("static");
        if (_static) {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        BlockStatement block = parser.next(BlockStatementParselet.class);
        return block == null ? null : new InitializerDeclaration(_static, block);
    }

}