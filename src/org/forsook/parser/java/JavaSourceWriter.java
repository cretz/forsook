package org.forsook.parser.java;

import java.util.List;

import org.forsook.parser.java.ast.array.ArrayInitializerExpression;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.AnnotationTypeBody;
import org.forsook.parser.java.ast.decl.AnnotationTypeDeclaration;
import org.forsook.parser.java.ast.decl.AnnotationTypeElementDeclaration;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceBody;
import org.forsook.parser.java.ast.decl.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.decl.ConstructorDeclaration;
import org.forsook.parser.java.ast.decl.ElementValue;
import org.forsook.parser.java.ast.decl.EnumBody;
import org.forsook.parser.java.ast.decl.EnumConstantDeclaration;
import org.forsook.parser.java.ast.decl.EnumDeclaration;
import org.forsook.parser.java.ast.decl.ExplicitConstructorInvocationStatement;
import org.forsook.parser.java.ast.decl.FieldDeclaration;
import org.forsook.parser.java.ast.decl.InitializerDeclaration;
import org.forsook.parser.java.ast.decl.MarkerAnnotationExpression;
import org.forsook.parser.java.ast.decl.MethodDeclaration;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.NonWildTypeArguments;
import org.forsook.parser.java.ast.decl.NormalAnnotationExpression;
import org.forsook.parser.java.ast.decl.NormalAnnotationExpression.ElementValuePair;
import org.forsook.parser.java.ast.decl.Parameter;
import org.forsook.parser.java.ast.decl.SingleElementAnnotationExpression;
import org.forsook.parser.java.ast.decl.TypeBody;
import org.forsook.parser.java.ast.decl.VariableDeclarator;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression.AdditiveOperator;
import org.forsook.parser.java.ast.expression.AndOperatorExpression;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.ArrayCreationExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.ClassExpression;
import org.forsook.parser.java.ast.expression.ClassInstanceCreationExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Dimension;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression.EqualityOperator;
import org.forsook.parser.java.ast.expression.ExclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.InclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression.MultiplicativeOperator;
import org.forsook.parser.java.ast.expression.NegatedExpression;
import org.forsook.parser.java.ast.expression.ParenthesizedExpression;
import org.forsook.parser.java.ast.expression.PostfixIncrementExpression;
import org.forsook.parser.java.ast.expression.PrefixIncrementExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression.RelationalOperator;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression.ShiftOperator;
import org.forsook.parser.java.ast.expression.SignedExpression;
import org.forsook.parser.java.ast.expression.ThisExpression;
import org.forsook.parser.java.ast.lexical.BlockComment;
import org.forsook.parser.java.ast.lexical.Comment;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.lexical.LineComment;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.WhiteSpace;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.ast.packag.SingleStaticImportDeclaration;
import org.forsook.parser.java.ast.packag.SingleTypeImportDeclaration;
import org.forsook.parser.java.ast.packag.StaticOnDemandImportDeclaration;
import org.forsook.parser.java.ast.packag.TypeOnDemandImportDeclaration;
import org.forsook.parser.java.ast.statement.AssertStatement;
import org.forsook.parser.java.ast.statement.BasicForStatement;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.BreakStatement;
import org.forsook.parser.java.ast.statement.CatchClause;
import org.forsook.parser.java.ast.statement.ContinueStatement;
import org.forsook.parser.java.ast.statement.DoStatement;
import org.forsook.parser.java.ast.statement.EmptyStatement;
import org.forsook.parser.java.ast.statement.EnhancedForStatement;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.IfStatement;
import org.forsook.parser.java.ast.statement.LabeledStatement;
import org.forsook.parser.java.ast.statement.LocalClassDeclarationStatement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationStatement;
import org.forsook.parser.java.ast.statement.Resource;
import org.forsook.parser.java.ast.statement.ReturnStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.SwitchBlockStatementGroup;
import org.forsook.parser.java.ast.statement.SwitchStatement;
import org.forsook.parser.java.ast.statement.SynchronizedStatement;
import org.forsook.parser.java.ast.statement.ThrowStatement;
import org.forsook.parser.java.ast.statement.TryStatement;
import org.forsook.parser.java.ast.statement.WhileStatement;
import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.TypeArguments;
import org.forsook.parser.java.ast.type.TypeParameter;
import org.forsook.parser.java.ast.type.VoidType;
import org.forsook.parser.java.ast.type.WildcardType;
import org.forsook.parser.java.visitor.JavaModelVisitor;

public class JavaSourceWriter {

    private String indent = "    ";
    private String newline = "\n";
    private StringBuilder builder;
    private int statementDepth = 0;
    
    public StringBuilder build(Object object) {
        builder = new StringBuilder();
        new ToStringVisitor().visit(object);
        return builder;
    }
    
    private void indent() {
        indent(statementDepth);
    }
    
    private void indent(int depth) {
        for (int i = 0; i < depth; i++) {
            builder.append(indent);
        }
    }
    
    private class ToStringVisitor extends JavaModelVisitor {

        private void visitSeparated(List<?> list, String separator) {
            visitSeparated(list, separator, false);
        }
        
        private void visitSeparated(List<?> list, String separator, boolean indentBeforeItem) {
            boolean first = true;
            for (Object item : list) {
                if (!first) {
                    builder.append(separator);
                } else {
                    first = false;
                }
                if (indentBeforeItem) {
                    indent();
                }
                visit(item);
            }
        }
        
        private void visitBlockOrIndentedStatement(Statement statement) {
            if (!(statement instanceof BlockStatement)) {
                builder.append(newline);
                statementDepth++;
            }
            visit(statement);
            if (!(statement instanceof BlockStatement)) {
                statementDepth--;
            }
        }
        
        @Override
        public void visit(AdditiveOperatorExpression a) {
            visit(a.getLeft());
            builder.append(a.getOperator() == AdditiveOperator.PLUS ? " + " : " - ");
            visit(a.getRight());
        }
        @Override
        public void visit(AndOperatorExpression a) {
            visit(a.getLeft());
            builder.append(" & ");
            visit(a.getRight());
        }

        @Override
        public void visit(AnnotationTypeBody a) {
            visit((TypeBody) a);
        }

        @Override
        public void visit(AnnotationTypeDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            builder.append("@interface ");
            visit(a.getName());
            builder.append(' ');
            visit(a.getBody());
        }

        @Override
        public void visit(AnnotationTypeElementDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            for (AnnotationExpression annotation : a.getAnnotations()) {
                indent();
                visit(annotation);
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            visit(a.getType());
            builder.append(' ');
            visit(a.getName());
            builder.append("()");
            if (a.getDefaultValue() != null) {
                builder.append(" default ");
                visit(a.getDefaultValue());
                builder.append(';');
            }
        }

        @Override
        public void visit(ArrayAccessExpression a) {
            visit(a.getName());
            builder.append('[');
            visit(a.getIndex());
            builder.append(']');
        }

        @Override
        public void visit(ArrayCreationExpression a) {
            builder.append("new ");
            visit(a.getType());
            for (Dimension dimension : a.getDimensions()) {
                builder.append('[');
                if (dimension.getExpression() != null) {
                    visit(dimension.getExpression());
                }
                builder.append(']');
            }
            if (a.getInitializer() != null) {
                builder.append(' ');
                visit(a.getInitializer());
            }
        }

        @Override
        public void visit(ArrayInitializerExpression a) {
            builder.append("{ ");
            visitSeparated(a.getValues(), ", ");
            builder.append(" }");
        }

        @Override
        public void visit(ArrayType a) {
            visit(a.getType());
            builder.append("[]");
        }

        @Override
        public void visit(AssertStatement a) {
            indent();
            builder.append("assert ");
            visit(a.getCondition());
            if (a.getMessage() != null) {
                builder.append(" : ");
                visit(a.getMessage());
            }
            builder.append(';');
        }

        @Override
        public void visit(AssignmentOperatorExpression a) {
            visit(a.getLeft());
            builder.append(' ').append(a.getOperator()).append(' ');
            visit(a.getRight());
        }

        @Override
        public void visit(BasicForStatement a) {
            indent();
            builder.append("for (");
            visitSeparated(a.getInit(), ", ");
            builder.append(';');
            if (a.getCompare() != null) {
                builder.append(' ');
                visit(a.getCompare());
            }
            builder.append(';');
            if (a.getUpdate() != null && !a.getUpdate().isEmpty()) {
                builder.append(' ');
                visitSeparated(a.getUpdate(), ", ");
            }
            builder.append(") ");
            visitBlockOrIndentedStatement(a.getBody());
        }

        @Override
        public void visit(BlockStatement a) {
            builder.append('{');
            if (a.getStatements().isEmpty()) {
                builder.append(newline);
                indent();
                builder.append("}");
            } else {
                statementDepth++;
                builder.append(newline);
                visitSeparated(a.getStatements(), newline);
                builder.append(newline);
                statementDepth--;
                indent();
                builder.append('}');
            }
        }

        @Override
        public void visit(BreakStatement a) {
            indent();
            builder.append("break");
            if (a.getLabel() != null) {
                builder.append(' ');
                visit(a.getLabel());
            }
            builder.append(';');
        }

        @Override
        public void visit(CastExpression a) {
            builder.append('(');
            visit(a.getType());
            builder.append(") ");
            visit(a.getExpression());
        }

        @Override
        public void visit(CatchClause a) {
            builder.append(" catch (");
            if (!a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), " ");
                builder.append(' ');
            }
            if (a.isFinalPresent()) {
                builder.append("final ");
            }
            visitSeparated(a.getTypes(), "|");
            builder.append(' ');
            visit(a.getName());
            builder.append(") ");
            visit(a.getBlock());
        }

        @Override
        public void visit(ClassExpression a) {
            visit(a.getType());
            builder.append(".class");
        }

        @Override
        public void visit(ClassInstanceCreationExpression a) {
            if (a.getScope() != null) {
                visit(a.getScope());
                builder.append('.');
            }
            builder.append("new ");
            if (a.getPreTypeArguments() != null) {
                visit(a.getPreTypeArguments());
            }
            visit(a.getName());
            if (a.getPostTypeArguments() != null) {
                visit(a.getPostTypeArguments());
            }
            builder.append('(');
            visitSeparated(a.getArguments(), ", ");
            builder.append(')');
            if (a.getAnonymousBody() != null) {
                builder.append(' ');
                visit(a.getAnonymousBody());
            }
        }
        
        @Override
        public void visit(ClassOrInterfaceBody a) {
            visit((TypeBody) a);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), newline, true);
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            builder.append(a.isInterface() ? "interface " : "class ");
            visit(a.getName());
            if (a.getTypeParameters() != null && !a.getTypeParameters().isEmpty()) {
                builder.append('<');
                visitSeparated(a.getTypeParameters(), ", ");
                builder.append('>');
            }
            builder.append(' ');
            if (a.getExtendsList() != null && !a.getExtendsList().isEmpty()) {
                builder.append("extends ");
                visitSeparated(a.getExtendsList(), ", ");
                builder.append(' ');
            }
            if (a.getImplementsList() != null && !a.getImplementsList().isEmpty()) {
                builder.append("implements ");
                visitSeparated(a.getImplementsList(), ", ");
                builder.append(' ');
            }
            visit(a.getBody());
        }

        @Override
        public void visit(ClassOrInterfaceType a) {
            if (a.getPrevious() != null) {
                visit(a.getPrevious());
                builder.append('.');
            }
            visit(a.getName());
            if (a.getTypeArguments() != null) {
                visit(a.getTypeArguments());
            }
        }
        
        @Override
        public void visit(Comment a) {
            if (a instanceof LineComment) {
                builder.append("//").append(a.getText());
            } else if (a instanceof BlockComment) {
                builder.append("/*").append(a.getText()).append("*/");
            } else if (a instanceof JavadocComment) {
                visit((JavadocComment) a);
            }
        }

        @Override
        public void visit(CompilationUnit a) {
            statementDepth = 0;
            if (a.getPackageDeclaration() != null) {
                visit(a.getPackageDeclaration());
                builder.append(newline).append(newline);
            }
            if (a.getImports() != null & !a.getImports().isEmpty()) {
                visitSeparated(a.getImports(), newline);
                builder.append(newline).append(newline);
            }
            visitSeparated(a.getTypes(), newline + newline);
            builder.append(newline);
        }

        @Override
        public void visit(ConditionalAndOperatorExpression a) {
            visit(a.getLeft());
            builder.append(" && ");
            visit(a.getRight());
        }

        @Override
        public void visit(ConditionalOperatorExpression a) {
            visit(a.getCondition());
            builder.append(" ? ");
            visit(a.getThenExpression());
            builder.append(" : ");
            visit(a.getElseExpression());
        }

        @Override
        public void visit(ConditionalOrOperatorExpression a) {
            visit(a.getLeft());
            builder.append(" || ");
            visit(a.getRight());
        }

        @Override
        public void visit(ConstructorDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), newline, true);
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            if (a.getTypeParameters() != null && !a.getTypeParameters().isEmpty()) {
                builder.append('<');
                visitSeparated(a.getTypeParameters(), ", ");
                builder.append("> ");
            }
            visit(a.getName());
            builder.append('(');
            visitSeparated(a.getParameters(), ", ");
            builder.append(") ");
            if (a.getThrowsList() != null && !a.getThrowsList().isEmpty()) {
                builder.append("throws ");
                visitSeparated(a.getThrowsList(), ", ");
                builder.append(' ');
            }
            visit(a.getBlock());
        }

        @Override
        public void visit(ContinueStatement a) {
            indent();
            builder.append("continue");
            if (a.getLabel() != null) {
                builder.append(' ');
                visit(a.getLabel());
            }
            builder.append(';');
        }

        @Override
        public void visit(DoStatement a) {
            indent();
            builder.append("do ");
            visitBlockOrIndentedStatement(a.getStatement());
            if (a.getStatement() instanceof BlockStatement) {
                builder.append(' ');
            } else {
                builder.append(newline);
            }
            builder.append("while (");
            visit(a.getCondition());
            builder.append(");");
        }

        @Override
        public void visit(ElementValue a) {
            visit(a.getValue());
        }

        @Override
        public void visit(EmptyStatement a) {
            indent();
            builder.append(';');
        }

        @Override
        public void visit(EnhancedForStatement a) {
            indent();
            builder.append("for (");
            visit(a.getVar());
            builder.append(" : ");
            visit(a.getIterable());
            builder.append(") ");
            visitBlockOrIndentedStatement(a.getBody());
        }

        @Override
        @SuppressWarnings("rawtypes")
        public void visit(Enum a) {
            builder.append(a);
        }

        @Override
        public void visit(EnumBody a) {
            builder.append('{').append(newline);
            statementDepth++;
            visitSeparated(a.getConstants(), ',' + newline);
            builder.append(';').append(newline);
            if (a.getMembers() != null && !a.getMembers().isEmpty()) {
                builder.append(newline);
                visitSeparated(a.getMembers(), newline + newline);
                builder.append(newline);
            }
            statementDepth--;
            indent();
            builder.append('}');
        }

        @Override
        public void visit(EnumConstantDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), newline, true);
                builder.append(newline);
            }
            indent();
            visit(a.getName());
            if (a.getArguments() != null && !a.getArguments().isEmpty()) {
                builder.append('(');
                visitSeparated(a.getArguments(), ", ");
                builder.append(')');
            }
            if (a.getBody() != null) {
                builder.append(' ');
                visit(a.getBody());
            }
        }

        @Override
        public void visit(EnumDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), newline, true);
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            builder.append("enum ");
            visit(a.getName());
            builder.append(' ');
            if (a.getImplementsList() != null && !a.getImplementsList().isEmpty()) {
                builder.append("implements ");
                visitSeparated(a.getImplementsList(), ", ");
                builder.append(' ');
            }
            visit (a.getBody());
        }

        @Override
        public void visit(EqualityOperatorExpression a) {
            visit(a.getLeft());
            builder.append(a.getOperator() == EqualityOperator.EQUAL ?
                    " == " : " != ");
            visit(a.getRight());
        }

        @Override
        public void visit(ExclusiveOrOperatorExpression a) {
            visit(a.getLeft());
            builder.append(" ^ ");
            visit(a.getRight());
        }

        @Override
        public void visit(ExplicitConstructorInvocationStatement a) {
            indent();
            if (a.getScope() != null) {
                visit(a.getScope());
                builder.append('.');
            }
            if (a.getTypeArguments() != null) {
                visit(a.getTypeArguments());
            }
            builder.append(a.isThisPresent() ? "this(" : "super(");
            visitSeparated(a.getArguments(), ", ");
            builder.append(");");
        }

        @Override
        public void visit(ExpressionStatement a) {
            indent();
            visit(a.getExpression());
            builder.append(';');
        }

        @Override
        public void visit(FieldAccessExpression a) {
            if (a.getClassName() != null) {
                visit(a.getClassName());
                builder.append('.');
            } else if (a.getScope() != null) {
                visit(a.getScope());
                builder.append('.');
            }
            if (a.isSuperPresent()) {
                builder.append("super.");
            }
            visit(a.getField());
        }

        @Override
        public void visit(FieldDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), newline, true);
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            visit(a.getType());
            builder.append(' ');
            visitSeparated(a.getVariables(), ", ");
            builder.append(';');
        }

        @Override
        public void visit(Identifier a) {
            builder.append(a.getValue());
        }

        @Override
        public void visit(IfStatement a) {
            visit(a, true);
        }
        
        public void visit(IfStatement a, boolean indent) {
            if (indent) {
                indent();
            }
            builder.append("if (");
            visit(a.getCondition());
            builder.append(") ");
            visitBlockOrIndentedStatement(a.getThenStatement());
            if (a.getElseStatement() != null) {
                builder.append(" else ");
                //if statements start right here
                if (a.getElseStatement() instanceof IfStatement) {
                    visit((IfStatement) a.getElseStatement(), false);
                } else {
                    visitBlockOrIndentedStatement(a.getElseStatement());
                }
            }
        }

        @Override
        public void visit(InclusiveOrOperatorExpression a) {
            visit(a.getLeft());
            builder.append(" | ");
            visit(a.getRight());
        }

        @Override
        public void visit(InitializerDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
            }
            indent();
            if (a.isStatic()) {
                builder.append("static ");
            }
            visit(a.getBlock());
        }

        @Override
        public void visit(LabeledStatement a) {
            indent();
            visit(a.getIdentifier());
            builder.append(":").append(newline);
            statementDepth++;
            visit(a.getStatement());
            statementDepth--;
        }

        @Override
        public void visit(LiteralExpression a) {
            builder.append(a.getValue());
        }
        
        @Override
        public void visit(LocalClassDeclarationStatement a) {
            visit(a.getDeclaration());
        }
        
        @Override
        public void visit(LocalVariableDeclarationStatement a) {
            indent();
            visit(a.getExpression());
            builder.append(';');
        }

        @Override
        public void visit(LocalVariableDeclarationExpression a) {
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), " ");
                builder.append(' ');
            }
            if (a.isFinal()) {
                builder.append("final ");
            }
            visit(a.getType());
            builder.append(' ');
            visitSeparated(a.getVariables(), ", ");
        }

        @Override
        public void visit(MarkerAnnotationExpression a) {
            builder.append('@');
            visit(a.getName());
        }

        @Override
        public void visit(MethodDeclaration a) {
            if (a.getJavadoc() != null) {
                visit(a.getJavadoc());
                builder.append(newline);
            }
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), newline, true);
                builder.append(newline);
            }
            indent();
            for (Modifier modifier : a.getModifiers()) {
                builder.append(modifier.getLowerCase()).append(' ');
            }
            if (a.getTypeParameters() != null && !a.getTypeParameters().isEmpty()) {
                builder.append('<');
                visitSeparated(a.getTypeParameters(), ", ");
                builder.append("> ");
            }
            visit(a.getResult());
            builder.append(' ');
            visit(a.getName());
            builder.append('(');
            visitSeparated(a.getParameters(), ", ");
            builder.append(')');
            if (a.getThrowsList() == null && !a.getThrowsList().isEmpty()) {
                builder.append(" throws ");
                visitSeparated(a.getThrowsList(), ", ");
            }
            if (a.getBlock() != null) {
                builder.append(' ');
                visit(a.getBlock());
            } else {
                builder.append(';');
            }
        }

        @Override
        public void visit(MethodInvocationExpression a) {
            if (a.getScope() != null) {
                visit(a.getScope());
                builder.append('.');
            } else if (a.getClassName() != null) {
                visit(a.getClassName());
                builder.append('.');
            }
            if (a.isSuperPresent()) {
                builder.append("super.");
            }
            if (a.getTypeArguments() != null) {
                visit(a.getTypeArguments());
            }
            visit(a.getMethodName());
            builder.append('(');
            visitSeparated(a.getArguments(), ", ");
            builder.append(')');
        }

        @Override
        public void visit(Modifier a) {
            builder.append(a.getLowerCase());
        }

        @Override
        public void visit(MultiplicativeOperatorExpression a) {
            visit(a.getLeft());
            builder.append(a.getOperator() == MultiplicativeOperator.MULTIPLY ?
                    " * " : (a.getOperator() == MultiplicativeOperator.DIVIDE ?
                            " / " : " % "));
            visit(a.getRight());
        }

        @Override
        public void visit(NegatedExpression a) {
            builder.append(a.isNot() ? '!' : '~');
            visit(a.getExpression());
        }
        
        @Override
        public void visit(NonWildTypeArguments a) {
            builder.append('<');
            visitSeparated(a.getTypes(), ", ");
            builder.append('>');
        }

        @Override
        public void visit(NormalAnnotationExpression a) {
            builder.append('@');
            visit(a.getName());
            builder.append('(');
            visitSeparated(a.getPairs(), ", ");
            builder.append(')');
        }

        @Override
        public void visit(PackageDeclaration a) {
            builder.append("package ");
            visit(a.getName());
            builder.append(';');
        }

        @Override
        public void visit(Parameter a) {
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), " ");
                builder.append(' ');
            }
            if (a.isFinalPresent()) {
                builder.append("final ");
            }
            visit(a.getType());
            if (a.isVarArgs()) {
                builder.append("...");
            }
            builder.append(' ');
            visit(a.getName());
        }

        @Override
        public void visit(ParenthesizedExpression a) {
            builder.append('(');
            visit(a.getExpression());
            builder.append(')');
        }

        @Override
        public void visit(PostfixIncrementExpression a) {
            visit(a.getExpression());
            builder.append(a.isIncrement() ? "++" : "--");
        }

        @Override
        public void visit(PrefixIncrementExpression a) {
            builder.append(a.isIncrement() ? "++" : "--");
            visit(a.getExpression());
        }
        
        @Override
        public void visit(PrimitiveType a) {
            builder.append(a.getType().toString().toLowerCase());
        }

        @Override
        public void visit(QualifiedName a) {
            visitSeparated(a.getIdentifiers(), ".");
        }

        @Override
        public void visit(RelationalOperatorExpression a) {
            visit(a.getLeft());
            if (a.getOperator() == RelationalOperator.GREATER_THAN) {
                builder.append(" > ");
            } else if (a.getOperator() == RelationalOperator.GREATER_THAN_OR_EQUAL) {
                builder.append(" >= ");
            } else if (a.getOperator() == RelationalOperator.INSTANCE_OF) {
                builder.append(" instanceof ");
            } else if (a.getOperator() == RelationalOperator.LESS_THAN) {
                builder.append(" < ");
            } else if (a.getOperator() == RelationalOperator.LESS_THAN_OR_EQUAL) {
                builder.append(" <= ");
            }
            visit(a.getRight());
        }

        @Override
        public void visit(Resource a) {
            if (a.getAnnotations() != null && !a.getAnnotations().isEmpty()) {
                visitSeparated(a.getAnnotations(), " ");
                builder.append(' ');
            }
            if (a.isFinalPresent()) {
                builder.append("final ");
            }
            visit(a.getType());
            builder.append(' ');
            visit(a.getName());
            builder.append(" = ");
            visit(a.getExpression());
        }

        @Override
        public void visit(ReturnStatement a) {
            indent();
            builder.append("return");
            if (a.getExpression() != null) {
                builder.append(' ');
                visit(a.getExpression());
            }
            builder.append(';');
        }

        @Override
        public void visit(ShiftOperatorExpression a) {
            visit(a.getLeft());
            builder.append(a.getOperator() == ShiftOperator.LEFT ?
                    " << " : (a.getOperator() == ShiftOperator.SIGNED_RIGHT ?
                            " >> " : " >>> "));
            visit(a.getRight());
        }

        @Override
        public void visit(SignedExpression a) {
            builder.append(a.isPositive() ? '+' : '-');
            visit(a.getExpression());
        }

        @Override
        public void visit(SingleElementAnnotationExpression a) {
            builder.append('@');
            visit(a.getName());
            builder.append('(');
            visit(a.getValue());
            builder.append(')');
        }

        @Override
        public void visit(SingleStaticImportDeclaration a) {
            builder.append("import static ");
            visit(a.getName());
            builder.append(';');
        }

        @Override
        public void visit(SingleTypeImportDeclaration a) {
            builder.append("import ");
            visit(a.getName());
            builder.append(';');
        }

        @Override
        public void visit(StaticOnDemandImportDeclaration a) {
            builder.append("import static ");
            visit(a.getName());
            builder.append(".*;");
        }

        @Override
        public void visit(SwitchBlockStatementGroup a) {
            for (Expression expr : a.getLabels()) {
                indent();
                builder.append("case ");
                visit(expr);
                builder.append(':').append(newline);
            }
            if (a.isDefaultPresent()) {
                indent();
                builder.append("default:").append(newline);
            }
            statementDepth++;
            for (Statement statement : a.getStatements()) {
                visit(statement);
                builder.append(newline);
            }
            statementDepth--;
        }

        @Override
        public void visit(SwitchStatement a) {
            indent();
            builder.append("switch (");
            visit(a.getSelector());
            builder.append(") {").append(newline);
            visitSeparated(a.getEntries(), "");
            indent();
            builder.append('}');
        }

        @Override
        public void visit(SynchronizedStatement a) {
            indent();
            builder.append("synchronized (");
            visit(a.getExpression());
            builder.append(") ");
            visit(a.getBlock());
        }

        @Override
        public void visit(ThisExpression a) {
            if (a.getName() != null) {
                visit(a.getName());
                builder.append('.');
            }
            builder.append("this");
        }

        @Override
        public void visit(ThrowStatement a) {
            indent();
            builder.append("throw ");
            visit(a.getExpression());
            builder.append(';');
        }

        @Override
        public void visit(TryStatement a) {
            indent();
            builder.append("try ");
            visit(a.getTryBlock());
            if (a.getCatchClauses() != null && !a.getCatchClauses().isEmpty()) {
                visitSeparated(a.getCatchClauses(), " ");
            }
            if (a.getFinallyBlock() != null) {
                builder.append(" finally ");
                visit(a.getFinallyBlock());
            }
        }

        @Override
        public void visit(TypeArguments a) {
            builder.append('<');
            visitSeparated(a.getTypes(), ", ");
            builder.append('>');
        }

        @Override
        public void visit(TypeBody a) {
            builder.append('{').append(newline);
            statementDepth++;
            boolean first = true;
            for (BodyDeclaration member : a.getMembers()) {
                builder.append(newline);
                //we want two newlines for non-fields/enum constants
                if (!(member instanceof FieldDeclaration) &&
                        !(member instanceof EnumConstantDeclaration) &&
                        !first) {
                    builder.append(newline);
                }
                if (first) {
                    first = false;
                }
                visit(member);
            }
            statementDepth--;
            builder.append(newline);
            indent();
            builder.append('}');
        }

        @Override
        public void visit(TypeOnDemandImportDeclaration a) {
            builder.append("import ");
            visit(a.getName());
            builder.append(".*;");
        }

        @Override
        public void visit(TypeParameter a) {
            visit(a.getName());
            if (a.getTypesBound() != null && !a.getTypesBound().isEmpty()) {
                builder.append(" extends ");
                visitSeparated(a.getTypesBound(), " & ");
            }
        }

        @Override
        public void visit(VariableDeclarator a) {
            visit(a.getId());
            if (a.getInit() != null) {
                builder.append(" = ");
                visit(a.getInit());
            }
        }

        @Override
        public void visit(VariableDeclaratorId a) {
            visit(a.getName());
            for (int i = 0; i < a.getArrayCount(); i++) {
                builder.append("[]");
            }
        }

        @Override
        public void visit(VoidType a) {
            builder.append("void");
        }

        @Override
        public void visit(WhileStatement a) {
            indent();
            builder.append("while (");
            visit(a.getCondition());
            builder.append(") ");
            visit(a.getBody());
        }

        @Override
        public void visit(WhiteSpace a) {
            for (int i = 0; i < a.getAmount(); i++) {
                builder.append(a.getType().toString());
            }
        }

        @Override
        public void visit(WildcardType a) {
            builder.append('?');
            if (a.getExtendsType() != null) {
                builder.append(" extends ");
                visit(a.getExtendsType());
            } else if (a.getSuperType() != null) {
                builder.append(" super ");
                visit(a.getSuperType());
            }
        }

        @Override
        public void visit(ElementValuePair a) {
            visit(a.getName());
            builder.append(" = ");
            visit(a.getValue());
        }

        @Override
        public void visit(JavadocComment a) {
            builder.append("/**").append(a.getText()).append("*/");
        }
    }
}
