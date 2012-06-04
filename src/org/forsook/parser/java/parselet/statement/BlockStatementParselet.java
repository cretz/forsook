package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.TypeDeclarationStatement;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;

@JlsReference("14.2")
@ParseletDefinition(
        name = "forsook.java.blockStatement",
        emits = BlockStatement.class,
        needs = { 
            ClassOrInterfaceDeclaration.class, 
            LocalVariableDeclarationExpression.class,
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
        //local class gets no javadoc IMO
        parseWhiteSpaceAndComments(parser);
        do {
            Statement stmt;
            //local class?
            ClassOrInterfaceDeclaration decl = parser.next(ClassOrInterfaceDeclaration.class);
            if (decl != null && !decl.isInterface()) {
                stmt = new TypeDeclarationStatement(decl);
            } else {
                //variable declaration?
                LocalVariableDeclarationExpression expr = parser.next(LocalVariableDeclarationExpression.class);
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
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (!parser.peekPresentAndSkip('}'));
        return new BlockStatement(statements);
    }

}
