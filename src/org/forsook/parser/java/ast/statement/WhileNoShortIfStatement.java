package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.12")
@SuppressWarnings("serial")
public class WhileNoShortIfStatement extends WhileStatement implements NoShortIfStatement {

    public WhileNoShortIfStatement() {
    }
    
    public WhileNoShortIfStatement(Expression condition, Statement body) {
        super(condition, body);
    }
}
