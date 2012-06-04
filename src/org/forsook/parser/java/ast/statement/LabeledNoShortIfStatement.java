package org.forsook.parser.java.ast.statement;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.lexical.Identifier;

@JlsReference("14.7")
@SuppressWarnings("serial")
public class LabeledNoShortIfStatement extends LabeledStatement implements NoShortIfStatement {

    public LabeledNoShortIfStatement() {
    }
    
    public LabeledNoShortIfStatement(Identifier identifier, Statement statement) {
        super(identifier, statement);
    }
}
