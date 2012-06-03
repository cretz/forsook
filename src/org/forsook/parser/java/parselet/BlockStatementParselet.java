package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.BlockStatement;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.ExpressionStatement;
import org.forsook.parser.java.ast.Statement;
import org.forsook.parser.java.ast.TypeDeclarationStatement;
import org.forsook.parser.java.ast.VariableDeclarationExpression;

@JlsReference("14.2")
@ParseletDefinition(
        name = "forsook.java.blockStatement",
        emits = BlockStatement.class,
        needs = { 
            ClassOrInterfaceDeclaration.class, 
            VariableDeclarationExpression.class,
            Statement.class
        }
)
public class BlockStatementParselet extends StatementParselet<BlockStatement> {

    @Override
    public BlockStatement parse(Parser parser) {
        if (!parser.peekPresentAndSkip('{')) {
            return null;
        }
        //statements
        List<Statement> statements = new ArrayList<Statement>();
        do {
            Statement stmt;
            //local class? (handles its own whitespace at beginning w/ javadoc and what not)
            ClassOrInterfaceDeclaration decl = parser.next(ClassOrInterfaceDeclaration.class);
            if (decl != null) {
                stmt = new TypeDeclarationStatement(decl);
            } else {
                //spacing
                parseWhiteSpaceAndComments(parser);
                //variable declaration?
                VariableDeclarationExpression expr = parser.next(VariableDeclarationExpression.class);
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
            statements.add(stmt);
        } while (!parser.peekPresentAndSkip('}'));
        return new BlockStatement(statements);
    }

}
