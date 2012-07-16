package org.forsook.parser.java.ast.expression;

public interface ScopedExpression {

    Expression getScope();
    void setScope(Expression scope);
}
