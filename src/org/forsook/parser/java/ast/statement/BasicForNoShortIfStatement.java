package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;

@JlsReference("14.14.1")
@SuppressWarnings("serial")
public class BasicForNoShortIfStatement extends BasicForStatement implements NoShortIfStatement {

    public BasicForNoShortIfStatement() {
    }
    
    public BasicForNoShortIfStatement(List<Expression> init, Expression compare,
            List<Expression> update, Statement body) {
        super(init, compare, update, body);
    }
}
