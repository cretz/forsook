package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;

@JlsReference("14.9")
@SuppressWarnings("serial")
public class IfNoShortIfStatement extends IfStatement implements NoShortIfStatement {

    public IfNoShortIfStatement() {
    }
    
    public IfNoShortIfStatement(Expression condition,
            Statement thenStatement, Statement elseStatement) {
        super(condition, thenStatement, elseStatement);
    }
}
