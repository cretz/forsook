package org.forsook.parser.java.parselet;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.BlockStatement;
import org.forsook.parser.java.ast.InitializerDeclaration;

@JlsReference({ "8.6", "8.7" })
@ParseletDefinition(
        name = "forsook.java.initializerDeclaration",
        emits = InitializerDeclaration.class,
        needs = BlockStatement.class
)
public class InitializerDeclarationParselet extends BodyDeclarationParselet<InitializerDeclaration> {

    @Override
    public InitializerDeclaration parse(Parser parser) {
        boolean _static = parser.peekPresentAndSkip("static");
        if (_static) {
            //spacing
            parseWhiteSpaceAndComments(parser);
        }
        BlockStatement block = parser.next(BlockStatement.class);
        return block == null ? null : new InitializerDeclaration(_static, block);
    }

}
