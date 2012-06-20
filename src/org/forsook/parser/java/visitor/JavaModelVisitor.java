package org.forsook.parser.java.visitor;

import java.util.List;
import org.forsook.parser.java.ast.JavaModel;
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
import org.forsook.parser.java.ast.expression.AdditiveExpression;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression;
import org.forsook.parser.java.ast.expression.AdditiveOperatorExpression.AdditiveOperator;
import org.forsook.parser.java.ast.expression.AndExpression;
import org.forsook.parser.java.ast.expression.AndOperatorExpression;
import org.forsook.parser.java.ast.expression.ArrayAccessExpression;
import org.forsook.parser.java.ast.expression.ArrayCreationExpression;
import org.forsook.parser.java.ast.expression.AssignmentExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression;
import org.forsook.parser.java.ast.expression.AssignmentOperatorExpression.AssignmentOperator;
import org.forsook.parser.java.ast.expression.CastExpression;
import org.forsook.parser.java.ast.expression.ClassExpression;
import org.forsook.parser.java.ast.expression.ClassInstanceCreationExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndExpression;
import org.forsook.parser.java.ast.expression.ConditionalAndOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalExpression;
import org.forsook.parser.java.ast.expression.ConditionalOperatorExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrExpression;
import org.forsook.parser.java.ast.expression.ConditionalOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Dimension;
import org.forsook.parser.java.ast.expression.EqualityExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression;
import org.forsook.parser.java.ast.expression.EqualityOperatorExpression.EqualityOperator;
import org.forsook.parser.java.ast.expression.ExclusiveOrExpression;
import org.forsook.parser.java.ast.expression.ExclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.expression.FieldAccessExpression;
import org.forsook.parser.java.ast.expression.InclusiveOrExpression;
import org.forsook.parser.java.ast.expression.InclusiveOrOperatorExpression;
import org.forsook.parser.java.ast.expression.MethodInvocationExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression;
import org.forsook.parser.java.ast.expression.MultiplicativeOperatorExpression.MultiplicativeOperator;
import org.forsook.parser.java.ast.expression.NegatedExpression;
import org.forsook.parser.java.ast.expression.ParenthesizedExpression;
import org.forsook.parser.java.ast.expression.PostfixExpression;
import org.forsook.parser.java.ast.expression.PostfixIncrementExpression;
import org.forsook.parser.java.ast.expression.PrefixIncrementExpression;
import org.forsook.parser.java.ast.expression.PrimaryExpression;
import org.forsook.parser.java.ast.expression.PrimaryNoNewArrayExpression;
import org.forsook.parser.java.ast.expression.RelationalExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression;
import org.forsook.parser.java.ast.expression.RelationalOperatorExpression.RelationalOperator;
import org.forsook.parser.java.ast.expression.ShiftExpression;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression;
import org.forsook.parser.java.ast.expression.ShiftOperatorExpression.ShiftOperator;
import org.forsook.parser.java.ast.expression.SignedExpression;
import org.forsook.parser.java.ast.expression.ThisExpression;
import org.forsook.parser.java.ast.expression.UnaryExpression;
import org.forsook.parser.java.ast.expression.UnaryNotPlusMinusExpression;
import org.forsook.parser.java.ast.lexical.Comment;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;
import org.forsook.parser.java.ast.lexical.LiteralExpression;
import org.forsook.parser.java.ast.lexical.LiteralExpression.LiteralExpressionType;
import org.forsook.parser.java.ast.lexical.WhiteSpace;
import org.forsook.parser.java.ast.lexical.WhiteSpace.WhiteSpaceType;
import org.forsook.parser.java.ast.name.QualifiedName;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.parser.java.ast.packag.ImportDeclaration;
import org.forsook.parser.java.ast.packag.PackageDeclaration;
import org.forsook.parser.java.ast.packag.SingleStaticImportDeclaration;
import org.forsook.parser.java.ast.packag.SingleTypeImportDeclaration;
import org.forsook.parser.java.ast.packag.StaticOnDemandImportDeclaration;
import org.forsook.parser.java.ast.packag.TypeDeclaration;
import org.forsook.parser.java.ast.packag.TypeOnDemandImportDeclaration;
import org.forsook.parser.java.ast.statement.AssertStatement;
import org.forsook.parser.java.ast.statement.BasicForNoShortIfStatement;
import org.forsook.parser.java.ast.statement.BasicForStatement;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.BreakStatement;
import org.forsook.parser.java.ast.statement.CatchClause;
import org.forsook.parser.java.ast.statement.ContinueStatement;
import org.forsook.parser.java.ast.statement.DoStatement;
import org.forsook.parser.java.ast.statement.EmptyStatement;
import org.forsook.parser.java.ast.statement.EnhancedForStatement;
import org.forsook.parser.java.ast.statement.ExpressionStatement;
import org.forsook.parser.java.ast.statement.ForStatement;
import org.forsook.parser.java.ast.statement.IfNoShortIfStatement;
import org.forsook.parser.java.ast.statement.IfStatement;
import org.forsook.parser.java.ast.statement.InnerBlockStatement;
import org.forsook.parser.java.ast.statement.LabeledNoShortIfStatement;
import org.forsook.parser.java.ast.statement.LabeledStatement;
import org.forsook.parser.java.ast.statement.LocalClassDeclarationStatement;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationExpression;
import org.forsook.parser.java.ast.statement.LocalVariableDeclarationStatement;
import org.forsook.parser.java.ast.statement.NoShortIfStatement;
import org.forsook.parser.java.ast.statement.Resource;
import org.forsook.parser.java.ast.statement.ReturnStatement;
import org.forsook.parser.java.ast.statement.Statement;
import org.forsook.parser.java.ast.statement.StatementWithoutTrailingSubstatement;
import org.forsook.parser.java.ast.statement.SwitchBlockStatementGroup;
import org.forsook.parser.java.ast.statement.SwitchStatement;
import org.forsook.parser.java.ast.statement.SynchronizedStatement;
import org.forsook.parser.java.ast.statement.ThrowStatement;
import org.forsook.parser.java.ast.statement.TryStatement;
import org.forsook.parser.java.ast.statement.WhileNoShortIfStatement;
import org.forsook.parser.java.ast.statement.WhileStatement;
import org.forsook.parser.java.ast.type.ArrayType;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.PrimitiveType;
import org.forsook.parser.java.ast.type.PrimitiveType.Primitive;
import org.forsook.parser.java.ast.type.ReferenceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.ast.type.TypeArguments;
import org.forsook.parser.java.ast.type.TypeParameter;
import org.forsook.parser.java.ast.type.VoidType;
import org.forsook.parser.java.ast.type.WildcardType;

@SuppressWarnings("all")
public class JavaModelVisitor {
    //generated by VisitorGenerator

    public void visit(AdditiveExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof MultiplicativeExpression) {
            visit((MultiplicativeExpression) a);
        } else if (a instanceof AdditiveOperatorExpression) {
            visit((AdditiveOperatorExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(AdditiveOperator a) {
        //do nothing with enum
    }

    public void visit(AdditiveOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getOperator();
        if (o != null) {
            visit((AdditiveOperator) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(AndExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof AndOperatorExpression) {
            visit((AndOperatorExpression) a);
        } else if (a instanceof EqualityExpression) {
            visit((EqualityExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(AndOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(AnnotationExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof SingleElementAnnotationExpression) {
            visit((SingleElementAnnotationExpression) a);
        } else if (a instanceof NormalAnnotationExpression) {
            visit((NormalAnnotationExpression) a);
        } else if (a instanceof MarkerAnnotationExpression) {
            visit((MarkerAnnotationExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(AnnotationTypeBody a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getMembers();
        if (o != null) {
            for (BodyDeclaration l : (List<BodyDeclaration>) o) {
                visit((BodyDeclaration) l);
            }
        }
    }

    public void visit(AnnotationTypeDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBody();
        if (o != null) {
            visit((TypeBody) o);
        }
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(AnnotationTypeElementDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
        o = a.getDefaultValue();
        if (o != null) {
            visit((ElementValue) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(ArrayAccessExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getIndex();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ArrayCreationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getDimensions();
        if (o != null) {
            for (Dimension l : (List<Dimension>) o) {
                visit((Dimension) l);
            }
        }
        o = a.getInitializer();
        if (o != null) {
            visit((ArrayInitializerExpression) o);
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(ArrayInitializerExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getValues();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
    }

    public void visit(ArrayType a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(AssertStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getMessage();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(AssignmentExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ConditionalExpression) {
            visit((ConditionalExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(AssignmentOperator a) {
        //do nothing with enum
    }

    public void visit(AssignmentOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getOperator();
        if (o != null) {
            visit((AssignmentOperator) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(BasicForNoShortIfStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getInit();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getCompare();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getUpdate();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getBody();
        if (o != null) {
            visit((Statement) o);
        }
    }

    public void visit(BasicForStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof BasicForNoShortIfStatement) {
            visit((BasicForNoShortIfStatement) a);
        }
        Object o = null;
        o = a.getInit();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getCompare();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getUpdate();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getBody();
        if (o != null) {
            visit((Statement) o);
        }
    }

    public void visit(BlockStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getStatements();
        if (o != null) {
            for (Statement l : (List<Statement>) o) {
                visit((Statement) l);
            }
        }
    }

    public void visit(BodyDeclaration a) {
        if (a == null) {
            return;
        } else if (a instanceof ClassOrInterfaceDeclaration) {
            visit((ClassOrInterfaceDeclaration) a);
        } else if (a instanceof InitializerDeclaration) {
            visit((InitializerDeclaration) a);
        } else if (a instanceof ConstructorDeclaration) {
            visit((ConstructorDeclaration) a);
        } else if (a instanceof AnnotationTypeElementDeclaration) {
            visit((AnnotationTypeElementDeclaration) a);
        } else if (a instanceof FieldDeclaration) {
            visit((FieldDeclaration) a);
        } else if (a instanceof MethodDeclaration) {
            visit((MethodDeclaration) a);
        } else if (a instanceof EnumConstantDeclaration) {
            visit((EnumConstantDeclaration) a);
        } else if (a instanceof EnumDeclaration) {
            visit((EnumDeclaration) a);
        } else if (a instanceof AnnotationTypeDeclaration) {
            visit((AnnotationTypeDeclaration) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(BreakStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLabel();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(CastExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(CatchClause a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
        o = a.getTypes();
        if (o != null) {
            for (Type l : (List<Type>) o) {
                visit((Type) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((VariableDeclaratorId) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(ClassExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(ClassInstanceCreationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getScope();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getArguments();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getPreTypeArguments();
        if (o != null) {
            visit((TypeArguments) o);
        }
        o = a.getPostTypeArguments();
        if (o != null) {
            visit((TypeArguments) o);
        }
        o = a.getAnonymousBody();
        if (o != null) {
            visit((ClassOrInterfaceBody) o);
        }
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(ClassOrInterfaceBody a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getMembers();
        if (o != null) {
            for (BodyDeclaration l : (List<BodyDeclaration>) o) {
                visit((BodyDeclaration) l);
            }
        }
    }

    public void visit(ClassOrInterfaceDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExtendsList();
        if (o != null) {
            for (ClassOrInterfaceType l : (List<ClassOrInterfaceType>) o) {
                visit((ClassOrInterfaceType) l);
            }
        }
        o = a.getImplementsList();
        if (o != null) {
            for (ClassOrInterfaceType l : (List<ClassOrInterfaceType>) o) {
                visit((ClassOrInterfaceType) l);
            }
        }
        o = a.getTypeParameters();
        if (o != null) {
            for (TypeParameter l : (List<TypeParameter>) o) {
                visit((TypeParameter) l);
            }
        }
        o = a.getBody();
        if (o != null) {
            visit((TypeBody) o);
        }
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(ClassOrInterfaceType a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTypeArguments();
        if (o != null) {
            visit((TypeArguments) o);
        }
        o = a.getPrevious();
        if (o != null) {
            visit((ClassOrInterfaceType) o);
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(Comment a) {
        if (a == null) {
            return;
        } else if (a instanceof JavadocComment) {
            visit((JavadocComment) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(CompilationUnit a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTypes();
        if (o != null) {
            for (TypeDeclaration l : (List<TypeDeclaration>) o) {
                visit((TypeDeclaration) l);
            }
        }
        o = a.getPackageDeclaration();
        if (o != null) {
            visit((PackageDeclaration) o);
        }
        o = a.getImports();
        if (o != null) {
            for (ImportDeclaration l : (List<ImportDeclaration>) o) {
                visit((ImportDeclaration) l);
            }
        }
    }

    public void visit(ConditionalAndExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof InclusiveOrExpression) {
            visit((InclusiveOrExpression) a);
        } else if (a instanceof ConditionalAndOperatorExpression) {
            visit((ConditionalAndOperatorExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(ConditionalAndOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ConditionalExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ConditionalOrExpression) {
            visit((ConditionalOrExpression) a);
        } else if (a instanceof ConditionalOperatorExpression) {
            visit((ConditionalOperatorExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(ConditionalOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getThenExpression();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getElseExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ConditionalOrExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ConditionalOrOperatorExpression) {
            visit((ConditionalOrOperatorExpression) a);
        } else if (a instanceof ConditionalAndExpression) {
            visit((ConditionalAndExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(ConditionalOrOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ConstructorDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
        o = a.getThrowsList();
        if (o != null) {
            for (ClassOrInterfaceType l : (List<ClassOrInterfaceType>) o) {
                visit((ClassOrInterfaceType) l);
            }
        }
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getTypeParameters();
        if (o != null) {
            for (TypeParameter l : (List<TypeParameter>) o) {
                visit((TypeParameter) l);
            }
        }
        o = a.getParameters();
        if (o != null) {
            for (Parameter l : (List<Parameter>) o) {
                visit((Parameter) l);
            }
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(ContinueStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLabel();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(Dimension a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(DoStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getStatement();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ElementValue a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getValue();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ElementValuePair a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getValue();
        if (o != null) {
            visit((ElementValue) o);
        }
    }

    public void visit(EmptyStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
    }

    public void visit(EnhancedForStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getVar();
        if (o != null) {
            visit((LocalVariableDeclarationExpression) o);
        }
        o = a.getIterable();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getBody();
        if (o != null) {
            visit((Statement) o);
        }
    }

    public void visit(Enum a) {
        if (a == null) {
            return;
        } else if (a instanceof AssignmentOperator) {
            visit((AssignmentOperator) a);
        } else if (a instanceof MultiplicativeOperator) {
            visit((MultiplicativeOperator) a);
        } else if (a instanceof Primitive) {
            visit((Primitive) a);
        } else if (a instanceof EqualityOperator) {
            visit((EqualityOperator) a);
        } else if (a instanceof AdditiveOperator) {
            visit((AdditiveOperator) a);
        } else if (a instanceof ShiftOperator) {
            visit((ShiftOperator) a);
        } else if (a instanceof RelationalOperator) {
            visit((RelationalOperator) a);
        } else if (a instanceof WhiteSpaceType) {
            visit((WhiteSpaceType) a);
        } else if (a instanceof LiteralExpressionType) {
            visit((LiteralExpressionType) a);
        } else if (a instanceof Modifier) {
            visit((Modifier) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(EnumBody a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getConstants();
        if (o != null) {
            for (EnumConstantDeclaration l : (List<EnumConstantDeclaration>) o) {
                visit((EnumConstantDeclaration) l);
            }
        }
        o = a.getMembers();
        if (o != null) {
            for (BodyDeclaration l : (List<BodyDeclaration>) o) {
                visit((BodyDeclaration) l);
            }
        }
    }

    public void visit(EnumConstantDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBody();
        if (o != null) {
            visit((ClassOrInterfaceBody) o);
        }
        o = a.getArguments();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(EnumDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getImplementsList();
        if (o != null) {
            for (ClassOrInterfaceType l : (List<ClassOrInterfaceType>) o) {
                visit((ClassOrInterfaceType) l);
            }
        }
        o = a.getBody();
        if (o != null) {
            visit((TypeBody) o);
        }
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(EqualityExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof EqualityOperatorExpression) {
            visit((EqualityOperatorExpression) a);
        } else if (a instanceof RelationalExpression) {
            visit((RelationalExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(EqualityOperator a) {
        //do nothing with enum
    }

    public void visit(EqualityOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getOperator();
        if (o != null) {
            visit((EqualityOperator) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ExclusiveOrExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ExclusiveOrOperatorExpression) {
            visit((ExclusiveOrOperatorExpression) a);
        } else if (a instanceof AndExpression) {
            visit((AndExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(ExclusiveOrOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ExplicitConstructorInvocationStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTypeArguments();
        if (o != null) {
            visit((NonWildTypeArguments) o);
        }
        o = a.getScope();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getArguments();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
    }

    public void visit(Expression a) {
        if (a == null) {
            return;
        } else if (a instanceof SingleElementAnnotationExpression) {
            visit((SingleElementAnnotationExpression) a);
        } else if (a instanceof LiteralExpression) {
            visit((LiteralExpression) a);
        } else if (a instanceof AnnotationExpression) {
            visit((AnnotationExpression) a);
        } else if (a instanceof LocalVariableDeclarationExpression) {
            visit((LocalVariableDeclarationExpression) a);
        } else if (a instanceof AdditiveOperatorExpression) {
            visit((AdditiveOperatorExpression) a);
        } else if (a instanceof MethodInvocationExpression) {
            visit((MethodInvocationExpression) a);
        } else if (a instanceof ShiftOperatorExpression) {
            visit((ShiftOperatorExpression) a);
        } else if (a instanceof FieldAccessExpression) {
            visit((FieldAccessExpression) a);
        } else if (a instanceof ThisExpression) {
            visit((ThisExpression) a);
        } else if (a instanceof ArrayInitializerExpression) {
            visit((ArrayInitializerExpression) a);
        } else if (a instanceof ClassInstanceCreationExpression) {
            visit((ClassInstanceCreationExpression) a);
        } else if (a instanceof MarkerAnnotationExpression) {
            visit((MarkerAnnotationExpression) a);
        } else if (a instanceof PrefixIncrementExpression) {
            visit((PrefixIncrementExpression) a);
        } else if (a instanceof NegatedExpression) {
            visit((NegatedExpression) a);
        } else if (a instanceof PostfixIncrementExpression) {
            visit((PostfixIncrementExpression) a);
        } else if (a instanceof InclusiveOrOperatorExpression) {
            visit((InclusiveOrOperatorExpression) a);
        } else if (a instanceof CastExpression) {
            visit((CastExpression) a);
        } else if (a instanceof MultiplicativeOperatorExpression) {
            visit((MultiplicativeOperatorExpression) a);
        } else if (a instanceof AssignmentOperatorExpression) {
            visit((AssignmentOperatorExpression) a);
        } else if (a instanceof ConditionalAndOperatorExpression) {
            visit((ConditionalAndOperatorExpression) a);
        } else if (a instanceof ArrayAccessExpression) {
            visit((ArrayAccessExpression) a);
        } else if (a instanceof ConditionalOrOperatorExpression) {
            visit((ConditionalOrOperatorExpression) a);
        } else if (a instanceof ParenthesizedExpression) {
            visit((ParenthesizedExpression) a);
        } else if (a instanceof AndOperatorExpression) {
            visit((AndOperatorExpression) a);
        } else if (a instanceof SignedExpression) {
            visit((SignedExpression) a);
        } else if (a instanceof ClassExpression) {
            visit((ClassExpression) a);
        } else if (a instanceof NormalAnnotationExpression) {
            visit((NormalAnnotationExpression) a);
        } else if (a instanceof ExclusiveOrOperatorExpression) {
            visit((ExclusiveOrOperatorExpression) a);
        } else if (a instanceof ConditionalOperatorExpression) {
            visit((ConditionalOperatorExpression) a);
        } else if (a instanceof ArrayCreationExpression) {
            visit((ArrayCreationExpression) a);
        } else if (a instanceof QualifiedName) {
            visit((QualifiedName) a);
        } else if (a instanceof EqualityOperatorExpression) {
            visit((EqualityOperatorExpression) a);
        } else if (a instanceof RelationalOperatorExpression) {
            visit((RelationalOperatorExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(ExpressionStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(FieldAccessExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getScope();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getField();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getClassName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(FieldDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getVariables();
        if (o != null) {
            for (VariableDeclarator l : (List<VariableDeclarator>) o) {
                visit((VariableDeclarator) l);
            }
        }
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(ForStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof BasicForNoShortIfStatement) {
            visit((BasicForNoShortIfStatement) a);
        } else if (a instanceof EnhancedForStatement) {
            visit((EnhancedForStatement) a);
        } else if (a instanceof BasicForStatement) {
            visit((BasicForStatement) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(Identifier a) {
        if (a == null) {
            return;
        }
        Object o = null;
    }

    public void visit(IfNoShortIfStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getThenStatement();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getElseStatement();
        if (o != null) {
            visit((Statement) o);
        }
    }

    public void visit(IfStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof IfNoShortIfStatement) {
            visit((IfNoShortIfStatement) a);
        }
        Object o = null;
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getThenStatement();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getElseStatement();
        if (o != null) {
            visit((Statement) o);
        }
    }

    public void visit(ImportDeclaration a) {
        if (a == null) {
            return;
        } else if (a instanceof TypeOnDemandImportDeclaration) {
            visit((TypeOnDemandImportDeclaration) a);
        } else if (a instanceof SingleStaticImportDeclaration) {
            visit((SingleStaticImportDeclaration) a);
        } else if (a instanceof SingleTypeImportDeclaration) {
            visit((SingleTypeImportDeclaration) a);
        } else if (a instanceof StaticOnDemandImportDeclaration) {
            visit((StaticOnDemandImportDeclaration) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(InclusiveOrExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof InclusiveOrOperatorExpression) {
            visit((InclusiveOrOperatorExpression) a);
        } else if (a instanceof ExclusiveOrExpression) {
            visit((ExclusiveOrExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(InclusiveOrOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(InitializerDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(InnerBlockStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof IfStatement) {
            visit((IfStatement) a);
        } else if (a instanceof LocalVariableDeclarationStatement) {
            visit((LocalVariableDeclarationStatement) a);
        } else if (a instanceof StatementWithoutTrailingSubstatement) {
            visit((StatementWithoutTrailingSubstatement) a);
        } else if (a instanceof WhileStatement) {
            visit((WhileStatement) a);
        } else if (a instanceof ForStatement) {
            visit((ForStatement) a);
        } else if (a instanceof LocalClassDeclarationStatement) {
            visit((LocalClassDeclarationStatement) a);
        } else if (a instanceof LabeledStatement) {
            visit((LabeledStatement) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(JavaModel a) {
        if (a == null) {
            return;
        } else if (a instanceof LocalVariableDeclarationStatement) {
            visit((LocalVariableDeclarationStatement) a);
        } else if (a instanceof LabeledNoShortIfStatement) {
            visit((LabeledNoShortIfStatement) a);
        } else if (a instanceof SynchronizedStatement) {
            visit((SynchronizedStatement) a);
        } else if (a instanceof EnumDeclaration) {
            visit((EnumDeclaration) a);
        } else if (a instanceof LocalVariableDeclarationExpression) {
            visit((LocalVariableDeclarationExpression) a);
        } else if (a instanceof AnnotationTypeDeclaration) {
            visit((AnnotationTypeDeclaration) a);
        } else if (a instanceof ClassOrInterfaceDeclaration) {
            visit((ClassOrInterfaceDeclaration) a);
        } else if (a instanceof AnnotationTypeElementDeclaration) {
            visit((AnnotationTypeElementDeclaration) a);
        } else if (a instanceof MarkerAnnotationExpression) {
            visit((MarkerAnnotationExpression) a);
        } else if (a instanceof InclusiveOrOperatorExpression) {
            visit((InclusiveOrOperatorExpression) a);
        } else if (a instanceof WhileNoShortIfStatement) {
            visit((WhileNoShortIfStatement) a);
        } else if (a instanceof TypeBody) {
            visit((TypeBody) a);
        } else if (a instanceof FieldDeclaration) {
            visit((FieldDeclaration) a);
        } else if (a instanceof WhiteSpace) {
            visit((WhiteSpace) a);
        } else if (a instanceof EnumConstantDeclaration) {
            visit((EnumConstantDeclaration) a);
        } else if (a instanceof LocalClassDeclarationStatement) {
            visit((LocalClassDeclarationStatement) a);
        } else if (a instanceof ConditionalAndOperatorExpression) {
            visit((ConditionalAndOperatorExpression) a);
        } else if (a instanceof ElementValuePair) {
            visit((ElementValuePair) a);
        } else if (a instanceof ContinueStatement) {
            visit((ContinueStatement) a);
        } else if (a instanceof ImportDeclaration) {
            visit((ImportDeclaration) a);
        } else if (a instanceof PackageDeclaration) {
            visit((PackageDeclaration) a);
        } else if (a instanceof EnumBody) {
            visit((EnumBody) a);
        } else if (a instanceof Dimension) {
            visit((Dimension) a);
        } else if (a instanceof EnhancedForStatement) {
            visit((EnhancedForStatement) a);
        } else if (a instanceof NormalAnnotationExpression) {
            visit((NormalAnnotationExpression) a);
        } else if (a instanceof SingleTypeImportDeclaration) {
            visit((SingleTypeImportDeclaration) a);
        } else if (a instanceof ExclusiveOrOperatorExpression) {
            visit((ExclusiveOrOperatorExpression) a);
        } else if (a instanceof ConditionalOperatorExpression) {
            visit((ConditionalOperatorExpression) a);
        } else if (a instanceof ArrayType) {
            visit((ArrayType) a);
        } else if (a instanceof SingleElementAnnotationExpression) {
            visit((SingleElementAnnotationExpression) a);
        } else if (a instanceof LiteralExpression) {
            visit((LiteralExpression) a);
        } else if (a instanceof AnnotationExpression) {
            visit((AnnotationExpression) a);
        } else if (a instanceof ExpressionStatement) {
            visit((ExpressionStatement) a);
        } else if (a instanceof ClassOrInterfaceType) {
            visit((ClassOrInterfaceType) a);
        } else if (a instanceof BasicForStatement) {
            visit((BasicForStatement) a);
        } else if (a instanceof MethodInvocationExpression) {
            visit((MethodInvocationExpression) a);
        } else if (a instanceof ShiftOperatorExpression) {
            visit((ShiftOperatorExpression) a);
        } else if (a instanceof SwitchStatement) {
            visit((SwitchStatement) a);
        } else if (a instanceof Statement) {
            visit((Statement) a);
        } else if (a instanceof StaticOnDemandImportDeclaration) {
            visit((StaticOnDemandImportDeclaration) a);
        } else if (a instanceof EmptyStatement) {
            visit((EmptyStatement) a);
        } else if (a instanceof CompilationUnit) {
            visit((CompilationUnit) a);
        } else if (a instanceof JavadocComment) {
            visit((JavadocComment) a);
        } else if (a instanceof ConditionalOrOperatorExpression) {
            visit((ConditionalOrOperatorExpression) a);
        } else if (a instanceof ClassExpression) {
            visit((ClassExpression) a);
        } else if (a instanceof ThrowStatement) {
            visit((ThrowStatement) a);
        } else if (a instanceof ConstructorDeclaration) {
            visit((ConstructorDeclaration) a);
        } else if (a instanceof CatchClause) {
            visit((CatchClause) a);
        } else if (a instanceof ArrayCreationExpression) {
            visit((ArrayCreationExpression) a);
        } else if (a instanceof IfNoShortIfStatement) {
            visit((IfNoShortIfStatement) a);
        } else if (a instanceof VariableDeclarator) {
            visit((VariableDeclarator) a);
        } else if (a instanceof ReferenceType) {
            visit((ReferenceType) a);
        } else if (a instanceof InitializerDeclaration) {
            visit((InitializerDeclaration) a);
        } else if (a instanceof BlockStatement) {
            visit((BlockStatement) a);
        } else if (a instanceof Expression) {
            visit((Expression) a);
        } else if (a instanceof Comment) {
            visit((Comment) a);
        } else if (a instanceof AdditiveOperatorExpression) {
            visit((AdditiveOperatorExpression) a);
        } else if (a instanceof IfStatement) {
            visit((IfStatement) a);
        } else if (a instanceof FieldAccessExpression) {
            visit((FieldAccessExpression) a);
        } else if (a instanceof ThisExpression) {
            visit((ThisExpression) a);
        } else if (a instanceof Parameter) {
            visit((Parameter) a);
        } else if (a instanceof ArrayInitializerExpression) {
            visit((ArrayInitializerExpression) a);
        } else if (a instanceof ClassInstanceCreationExpression) {
            visit((ClassInstanceCreationExpression) a);
        } else if (a instanceof ElementValue) {
            visit((ElementValue) a);
        } else if (a instanceof LabeledStatement) {
            visit((LabeledStatement) a);
        } else if (a instanceof WildcardType) {
            visit((WildcardType) a);
        } else if (a instanceof PrefixIncrementExpression) {
            visit((PrefixIncrementExpression) a);
        } else if (a instanceof NegatedExpression) {
            visit((NegatedExpression) a);
        } else if (a instanceof TypeParameter) {
            visit((TypeParameter) a);
        } else if (a instanceof BasicForNoShortIfStatement) {
            visit((BasicForNoShortIfStatement) a);
        } else if (a instanceof PostfixIncrementExpression) {
            visit((PostfixIncrementExpression) a);
        } else if (a instanceof SingleStaticImportDeclaration) {
            visit((SingleStaticImportDeclaration) a);
        } else if (a instanceof DoStatement) {
            visit((DoStatement) a);
        } else if (a instanceof ForStatement) {
            visit((ForStatement) a);
        } else if (a instanceof Identifier) {
            visit((Identifier) a);
        } else if (a instanceof ArrayAccessExpression) {
            visit((ArrayAccessExpression) a);
        } else if (a instanceof AndOperatorExpression) {
            visit((AndOperatorExpression) a);
        } else if (a instanceof PrimitiveType) {
            visit((PrimitiveType) a);
        } else if (a instanceof VoidType) {
            visit((VoidType) a);
        } else if (a instanceof QualifiedName) {
            visit((QualifiedName) a);
        } else if (a instanceof EqualityOperatorExpression) {
            visit((EqualityOperatorExpression) a);
        } else if (a instanceof VariableDeclaratorId) {
            visit((VariableDeclaratorId) a);
        } else if (a instanceof RelationalOperatorExpression) {
            visit((RelationalOperatorExpression) a);
        } else if (a instanceof ReturnStatement) {
            visit((ReturnStatement) a);
        } else if (a instanceof Type) {
            visit((Type) a);
        } else if (a instanceof Resource) {
            visit((Resource) a);
        } else if (a instanceof TypeOnDemandImportDeclaration) {
            visit((TypeOnDemandImportDeclaration) a);
        } else if (a instanceof ExplicitConstructorInvocationStatement) {
            visit((ExplicitConstructorInvocationStatement) a);
        } else if (a instanceof BreakStatement) {
            visit((BreakStatement) a);
        } else if (a instanceof CastExpression) {
            visit((CastExpression) a);
        } else if (a instanceof MultiplicativeOperatorExpression) {
            visit((MultiplicativeOperatorExpression) a);
        } else if (a instanceof AssignmentOperatorExpression) {
            visit((AssignmentOperatorExpression) a);
        } else if (a instanceof MethodDeclaration) {
            visit((MethodDeclaration) a);
        } else if (a instanceof NonWildTypeArguments) {
            visit((NonWildTypeArguments) a);
        } else if (a instanceof AssertStatement) {
            visit((AssertStatement) a);
        } else if (a instanceof AnnotationTypeBody) {
            visit((AnnotationTypeBody) a);
        } else if (a instanceof SwitchBlockStatementGroup) {
            visit((SwitchBlockStatementGroup) a);
        } else if (a instanceof TryStatement) {
            visit((TryStatement) a);
        } else if (a instanceof ParenthesizedExpression) {
            visit((ParenthesizedExpression) a);
        } else if (a instanceof ClassOrInterfaceBody) {
            visit((ClassOrInterfaceBody) a);
        } else if (a instanceof TypeArguments) {
            visit((TypeArguments) a);
        } else if (a instanceof SignedExpression) {
            visit((SignedExpression) a);
        } else if (a instanceof WhileStatement) {
            visit((WhileStatement) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(JavadocComment a) {
        if (a == null) {
            return;
        }
        Object o = null;
    }

    public void visit(LabeledNoShortIfStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getStatement();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getIdentifier();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(LabeledStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof LabeledNoShortIfStatement) {
            visit((LabeledNoShortIfStatement) a);
        }
        Object o = null;
        o = a.getStatement();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getIdentifier();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(LiteralExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getType();
        if (o != null) {
            visit((LiteralExpressionType) o);
        }
    }

    public void visit(LiteralExpressionType a) {
        //do nothing with enum
    }

    public void visit(LocalClassDeclarationStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getDeclaration();
        if (o != null) {
            visit((TypeDeclaration) o);
        }
    }

    public void visit(LocalVariableDeclarationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getVariables();
        if (o != null) {
            for (VariableDeclarator l : (List<VariableDeclarator>) o) {
                visit((VariableDeclarator) l);
            }
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(LocalVariableDeclarationStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((LocalVariableDeclarationExpression) o);
        }
    }

    public void visit(MarkerAnnotationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(MethodDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getResult();
        if (o != null) {
            visit((Type) o);
        }
        o = a.getBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
        o = a.getThrowsList();
        if (o != null) {
            for (ClassOrInterfaceType l : (List<ClassOrInterfaceType>) o) {
                visit((ClassOrInterfaceType) l);
            }
        }
        o = a.getModifiers();
        if (o != null) {
            for (Modifier l : (List<Modifier>) o) {
                visit((Modifier) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
        o = a.getTypeParameters();
        if (o != null) {
            for (TypeParameter l : (List<TypeParameter>) o) {
                visit((TypeParameter) l);
            }
        }
        o = a.getParameters();
        if (o != null) {
            for (Parameter l : (List<Parameter>) o) {
                visit((Parameter) l);
            }
        }
        o = a.getJavadoc();
        if (o != null) {
            visit((JavadocComment) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(MethodInvocationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTypeArguments();
        if (o != null) {
            visit((NonWildTypeArguments) o);
        }
        o = a.getScope();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getArguments();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
        o = a.getClassName();
        if (o != null) {
            visit((QualifiedName) o);
        }
        o = a.getMethodName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(Modifier a) {
        //do nothing with enum
    }

    public void visit(MultiplicativeExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof MultiplicativeOperatorExpression) {
            visit((MultiplicativeOperatorExpression) a);
        } else if (a instanceof UnaryExpression) {
            visit((UnaryExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(MultiplicativeOperator a) {
        //do nothing with enum
    }

    public void visit(MultiplicativeOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getOperator();
        if (o != null) {
            visit((MultiplicativeOperator) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(NegatedExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(NoShortIfStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof BasicForNoShortIfStatement) {
            visit((BasicForNoShortIfStatement) a);
        } else if (a instanceof StatementWithoutTrailingSubstatement) {
            visit((StatementWithoutTrailingSubstatement) a);
        } else if (a instanceof LabeledNoShortIfStatement) {
            visit((LabeledNoShortIfStatement) a);
        } else if (a instanceof WhileNoShortIfStatement) {
            visit((WhileNoShortIfStatement) a);
        } else if (a instanceof IfNoShortIfStatement) {
            visit((IfNoShortIfStatement) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(NonWildTypeArguments a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTypes();
        if (o != null) {
            for (Type l : (List<Type>) o) {
                visit((Type) l);
            }
        }
    }

    public void visit(NormalAnnotationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getPairs();
        if (o != null) {
            for (ElementValuePair l : (List<ElementValuePair>) o) {
                visit((ElementValuePair) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(Object a) {
        if (a == null) {
            return;
        } else if (a instanceof LocalVariableDeclarationStatement) {
            visit((LocalVariableDeclarationStatement) a);
        } else if (a instanceof LabeledNoShortIfStatement) {
            visit((LabeledNoShortIfStatement) a);
        } else if (a instanceof SynchronizedStatement) {
            visit((SynchronizedStatement) a);
        } else if (a instanceof EnumDeclaration) {
            visit((EnumDeclaration) a);
        } else if (a instanceof LocalVariableDeclarationExpression) {
            visit((LocalVariableDeclarationExpression) a);
        } else if (a instanceof AnnotationTypeDeclaration) {
            visit((AnnotationTypeDeclaration) a);
        } else if (a instanceof ClassOrInterfaceDeclaration) {
            visit((ClassOrInterfaceDeclaration) a);
        } else if (a instanceof AnnotationTypeElementDeclaration) {
            visit((AnnotationTypeElementDeclaration) a);
        } else if (a instanceof MarkerAnnotationExpression) {
            visit((MarkerAnnotationExpression) a);
        } else if (a instanceof InclusiveOrOperatorExpression) {
            visit((InclusiveOrOperatorExpression) a);
        } else if (a instanceof WhileNoShortIfStatement) {
            visit((WhileNoShortIfStatement) a);
        } else if (a instanceof TypeBody) {
            visit((TypeBody) a);
        } else if (a instanceof FieldDeclaration) {
            visit((FieldDeclaration) a);
        } else if (a instanceof WhiteSpace) {
            visit((WhiteSpace) a);
        } else if (a instanceof EnumConstantDeclaration) {
            visit((EnumConstantDeclaration) a);
        } else if (a instanceof LocalClassDeclarationStatement) {
            visit((LocalClassDeclarationStatement) a);
        } else if (a instanceof ConditionalAndOperatorExpression) {
            visit((ConditionalAndOperatorExpression) a);
        } else if (a instanceof ElementValuePair) {
            visit((ElementValuePair) a);
        } else if (a instanceof ContinueStatement) {
            visit((ContinueStatement) a);
        } else if (a instanceof ImportDeclaration) {
            visit((ImportDeclaration) a);
        } else if (a instanceof PackageDeclaration) {
            visit((PackageDeclaration) a);
        } else if (a instanceof EnumBody) {
            visit((EnumBody) a);
        } else if (a instanceof Dimension) {
            visit((Dimension) a);
        } else if (a instanceof EnhancedForStatement) {
            visit((EnhancedForStatement) a);
        } else if (a instanceof NormalAnnotationExpression) {
            visit((NormalAnnotationExpression) a);
        } else if (a instanceof MultiplicativeOperator) {
            visit((MultiplicativeOperator) a);
        } else if (a instanceof SingleTypeImportDeclaration) {
            visit((SingleTypeImportDeclaration) a);
        } else if (a instanceof ExclusiveOrOperatorExpression) {
            visit((ExclusiveOrOperatorExpression) a);
        } else if (a instanceof ConditionalOperatorExpression) {
            visit((ConditionalOperatorExpression) a);
        } else if (a instanceof LiteralExpressionType) {
            visit((LiteralExpressionType) a);
        } else if (a instanceof ArrayType) {
            visit((ArrayType) a);
        } else if (a instanceof SingleElementAnnotationExpression) {
            visit((SingleElementAnnotationExpression) a);
        } else if (a instanceof LiteralExpression) {
            visit((LiteralExpression) a);
        } else if (a instanceof AdditiveOperator) {
            visit((AdditiveOperator) a);
        } else if (a instanceof AnnotationExpression) {
            visit((AnnotationExpression) a);
        } else if (a instanceof ExpressionStatement) {
            visit((ExpressionStatement) a);
        } else if (a instanceof ClassOrInterfaceType) {
            visit((ClassOrInterfaceType) a);
        } else if (a instanceof BasicForStatement) {
            visit((BasicForStatement) a);
        } else if (a instanceof ShiftOperatorExpression) {
            visit((ShiftOperatorExpression) a);
        } else if (a instanceof MethodInvocationExpression) {
            visit((MethodInvocationExpression) a);
        } else if (a instanceof SwitchStatement) {
            visit((SwitchStatement) a);
        } else if (a instanceof Statement) {
            visit((Statement) a);
        } else if (a instanceof StaticOnDemandImportDeclaration) {
            visit((StaticOnDemandImportDeclaration) a);
        } else if (a instanceof EmptyStatement) {
            visit((EmptyStatement) a);
        } else if (a instanceof CompilationUnit) {
            visit((CompilationUnit) a);
        } else if (a instanceof JavadocComment) {
            visit((JavadocComment) a);
        } else if (a instanceof Modifier) {
            visit((Modifier) a);
        } else if (a instanceof ConditionalOrOperatorExpression) {
            visit((ConditionalOrOperatorExpression) a);
        } else if (a instanceof ClassExpression) {
            visit((ClassExpression) a);
        } else if (a instanceof ThrowStatement) {
            visit((ThrowStatement) a);
        } else if (a instanceof ConstructorDeclaration) {
            visit((ConstructorDeclaration) a);
        } else if (a instanceof CatchClause) {
            visit((CatchClause) a);
        } else if (a instanceof ArrayCreationExpression) {
            visit((ArrayCreationExpression) a);
        } else if (a instanceof IfNoShortIfStatement) {
            visit((IfNoShortIfStatement) a);
        } else if (a instanceof VariableDeclarator) {
            visit((VariableDeclarator) a);
        } else if (a instanceof ReferenceType) {
            visit((ReferenceType) a);
        } else if (a instanceof InitializerDeclaration) {
            visit((InitializerDeclaration) a);
        } else if (a instanceof BlockStatement) {
            visit((BlockStatement) a);
        } else if (a instanceof Expression) {
            visit((Expression) a);
        } else if (a instanceof Comment) {
            visit((Comment) a);
        } else if (a instanceof AdditiveOperatorExpression) {
            visit((AdditiveOperatorExpression) a);
        } else if (a instanceof IfStatement) {
            visit((IfStatement) a);
        } else if (a instanceof FieldAccessExpression) {
            visit((FieldAccessExpression) a);
        } else if (a instanceof ThisExpression) {
            visit((ThisExpression) a);
        } else if (a instanceof Parameter) {
            visit((Parameter) a);
        } else if (a instanceof ArrayInitializerExpression) {
            visit((ArrayInitializerExpression) a);
        } else if (a instanceof ClassInstanceCreationExpression) {
            visit((ClassInstanceCreationExpression) a);
        } else if (a instanceof ElementValue) {
            visit((ElementValue) a);
        } else if (a instanceof ShiftOperator) {
            visit((ShiftOperator) a);
        } else if (a instanceof LabeledStatement) {
            visit((LabeledStatement) a);
        } else if (a instanceof WildcardType) {
            visit((WildcardType) a);
        } else if (a instanceof PrefixIncrementExpression) {
            visit((PrefixIncrementExpression) a);
        } else if (a instanceof NegatedExpression) {
            visit((NegatedExpression) a);
        } else if (a instanceof TypeParameter) {
            visit((TypeParameter) a);
        } else if (a instanceof BasicForNoShortIfStatement) {
            visit((BasicForNoShortIfStatement) a);
        } else if (a instanceof PostfixIncrementExpression) {
            visit((PostfixIncrementExpression) a);
        } else if (a instanceof SingleStaticImportDeclaration) {
            visit((SingleStaticImportDeclaration) a);
        } else if (a instanceof DoStatement) {
            visit((DoStatement) a);
        } else if (a instanceof ForStatement) {
            visit((ForStatement) a);
        } else if (a instanceof EqualityOperator) {
            visit((EqualityOperator) a);
        } else if (a instanceof Identifier) {
            visit((Identifier) a);
        } else if (a instanceof ArrayAccessExpression) {
            visit((ArrayAccessExpression) a);
        } else if (a instanceof AndOperatorExpression) {
            visit((AndOperatorExpression) a);
        } else if (a instanceof PrimitiveType) {
            visit((PrimitiveType) a);
        } else if (a instanceof VoidType) {
            visit((VoidType) a);
        } else if (a instanceof AssignmentOperator) {
            visit((AssignmentOperator) a);
        } else if (a instanceof RelationalOperator) {
            visit((RelationalOperator) a);
        } else if (a instanceof QualifiedName) {
            visit((QualifiedName) a);
        } else if (a instanceof EqualityOperatorExpression) {
            visit((EqualityOperatorExpression) a);
        } else if (a instanceof VariableDeclaratorId) {
            visit((VariableDeclaratorId) a);
        } else if (a instanceof RelationalOperatorExpression) {
            visit((RelationalOperatorExpression) a);
        } else if (a instanceof ReturnStatement) {
            visit((ReturnStatement) a);
        } else if (a instanceof Type) {
            visit((Type) a);
        } else if (a instanceof TypeOnDemandImportDeclaration) {
            visit((TypeOnDemandImportDeclaration) a);
        } else if (a instanceof Resource) {
            visit((Resource) a);
        } else if (a instanceof Primitive) {
            visit((Primitive) a);
        } else if (a instanceof ExplicitConstructorInvocationStatement) {
            visit((ExplicitConstructorInvocationStatement) a);
        } else if (a instanceof BreakStatement) {
            visit((BreakStatement) a);
        } else if (a instanceof CastExpression) {
            visit((CastExpression) a);
        } else if (a instanceof MultiplicativeOperatorExpression) {
            visit((MultiplicativeOperatorExpression) a);
        } else if (a instanceof MethodDeclaration) {
            visit((MethodDeclaration) a);
        } else if (a instanceof AssignmentOperatorExpression) {
            visit((AssignmentOperatorExpression) a);
        } else if (a instanceof NonWildTypeArguments) {
            visit((NonWildTypeArguments) a);
        } else if (a instanceof AssertStatement) {
            visit((AssertStatement) a);
        } else if (a instanceof AnnotationTypeBody) {
            visit((AnnotationTypeBody) a);
        } else if (a instanceof WhiteSpaceType) {
            visit((WhiteSpaceType) a);
        } else if (a instanceof SwitchBlockStatementGroup) {
            visit((SwitchBlockStatementGroup) a);
        } else if (a instanceof TryStatement) {
            visit((TryStatement) a);
        } else if (a instanceof ParenthesizedExpression) {
            visit((ParenthesizedExpression) a);
        } else if (a instanceof ClassOrInterfaceBody) {
            visit((ClassOrInterfaceBody) a);
        } else if (a instanceof TypeArguments) {
            visit((TypeArguments) a);
        } else if (a instanceof SignedExpression) {
            visit((SignedExpression) a);
        } else if (a instanceof WhileStatement) {
            visit((WhileStatement) a);
        }
        Object o = null;
    }

    public void visit(PackageDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
    }

    public void visit(Parameter a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((VariableDeclaratorId) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(ParenthesizedExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(PostfixExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof PostfixIncrementExpression) {
            visit((PostfixIncrementExpression) a);
        } else if (a instanceof PrimaryExpression) {
            visit((PrimaryExpression) a);
        } else if (a instanceof QualifiedName) {
            visit((QualifiedName) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(PostfixIncrementExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(PrefixIncrementExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(PrimaryExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof PrimaryNoNewArrayExpression) {
            visit((PrimaryNoNewArrayExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(PrimaryNoNewArrayExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ParenthesizedExpression) {
            visit((ParenthesizedExpression) a);
        } else if (a instanceof MethodInvocationExpression) {
            visit((MethodInvocationExpression) a);
        } else if (a instanceof FieldAccessExpression) {
            visit((FieldAccessExpression) a);
        } else if (a instanceof ClassExpression) {
            visit((ClassExpression) a);
        } else if (a instanceof ThisExpression) {
            visit((ThisExpression) a);
        } else if (a instanceof ClassInstanceCreationExpression) {
            visit((ClassInstanceCreationExpression) a);
        } else if (a instanceof LiteralExpression) {
            visit((LiteralExpression) a);
        } else if (a instanceof ArrayAccessExpression) {
            visit((ArrayAccessExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(Primitive a) {
        //do nothing with enum
    }

    public void visit(PrimitiveType a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getType();
        if (o != null) {
            visit((Primitive) o);
        }
    }

    public void visit(QualifiedName a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getIdentifiers();
        if (o != null) {
            for (Identifier l : (List<Identifier>) o) {
                visit((Identifier) l);
            }
        }
    }

    public void visit(ReferenceType a) {
        if (a == null) {
            return;
        } else if (a instanceof ArrayType) {
            visit((ArrayType) a);
        } else if (a instanceof ClassOrInterfaceType) {
            visit((ClassOrInterfaceType) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(RelationalExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ShiftExpression) {
            visit((ShiftExpression) a);
        } else if (a instanceof RelationalOperatorExpression) {
            visit((RelationalOperatorExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(RelationalOperator a) {
        //do nothing with enum
    }

    public void visit(RelationalOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getOperator();
        if (o != null) {
            visit((RelationalOperator) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(Resource a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getName();
        if (o != null) {
            visit((VariableDeclaratorId) o);
        }
        o = a.getAnnotations();
        if (o != null) {
            for (AnnotationExpression l : (List<AnnotationExpression>) o) {
                visit((AnnotationExpression) l);
            }
        }
        o = a.getType();
        if (o != null) {
            visit((Type) o);
        }
    }

    public void visit(ReturnStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ShiftExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof ShiftOperatorExpression) {
            visit((ShiftOperatorExpression) a);
        } else if (a instanceof AdditiveExpression) {
            visit((AdditiveExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(ShiftOperator a) {
        //do nothing with enum
    }

    public void visit(ShiftOperatorExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getLeft();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getOperator();
        if (o != null) {
            visit((ShiftOperator) o);
        }
        o = a.getRight();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(SignedExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(SingleElementAnnotationExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getValue();
        if (o != null) {
            visit((ElementValue) o);
        }
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(SingleStaticImportDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(SingleTypeImportDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(Statement a) {
        if (a == null) {
            return;
        } else if (a instanceof ReturnStatement) {
            visit((ReturnStatement) a);
        } else if (a instanceof LocalVariableDeclarationStatement) {
            visit((LocalVariableDeclarationStatement) a);
        } else if (a instanceof LabeledNoShortIfStatement) {
            visit((LabeledNoShortIfStatement) a);
        } else if (a instanceof SynchronizedStatement) {
            visit((SynchronizedStatement) a);
        } else if (a instanceof BlockStatement) {
            visit((BlockStatement) a);
        } else if (a instanceof ExpressionStatement) {
            visit((ExpressionStatement) a);
        } else if (a instanceof BasicForStatement) {
            visit((BasicForStatement) a);
        } else if (a instanceof IfStatement) {
            visit((IfStatement) a);
        } else if (a instanceof SwitchStatement) {
            visit((SwitchStatement) a);
        } else if (a instanceof LabeledStatement) {
            visit((LabeledStatement) a);
        } else if (a instanceof BasicForNoShortIfStatement) {
            visit((BasicForNoShortIfStatement) a);
        } else if (a instanceof ExplicitConstructorInvocationStatement) {
            visit((ExplicitConstructorInvocationStatement) a);
        } else if (a instanceof BreakStatement) {
            visit((BreakStatement) a);
        } else if (a instanceof WhileNoShortIfStatement) {
            visit((WhileNoShortIfStatement) a);
        } else if (a instanceof DoStatement) {
            visit((DoStatement) a);
        } else if (a instanceof EmptyStatement) {
            visit((EmptyStatement) a);
        } else if (a instanceof LocalClassDeclarationStatement) {
            visit((LocalClassDeclarationStatement) a);
        } else if (a instanceof ForStatement) {
            visit((ForStatement) a);
        } else if (a instanceof ContinueStatement) {
            visit((ContinueStatement) a);
        } else if (a instanceof AssertStatement) {
            visit((AssertStatement) a);
        } else if (a instanceof TryStatement) {
            visit((TryStatement) a);
        } else if (a instanceof EnhancedForStatement) {
            visit((EnhancedForStatement) a);
        } else if (a instanceof ThrowStatement) {
            visit((ThrowStatement) a);
        } else if (a instanceof WhileStatement) {
            visit((WhileStatement) a);
        } else if (a instanceof IfNoShortIfStatement) {
            visit((IfNoShortIfStatement) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(StatementWithoutTrailingSubstatement a) {
        if (a == null) {
            return;
        } else if (a instanceof ReturnStatement) {
            visit((ReturnStatement) a);
        } else if (a instanceof SwitchStatement) {
            visit((SwitchStatement) a);
        } else if (a instanceof BreakStatement) {
            visit((BreakStatement) a);
        } else if (a instanceof ThrowStatement) {
            visit((ThrowStatement) a);
        } else if (a instanceof DoStatement) {
            visit((DoStatement) a);
        } else if (a instanceof SynchronizedStatement) {
            visit((SynchronizedStatement) a);
        } else if (a instanceof EmptyStatement) {
            visit((EmptyStatement) a);
        } else if (a instanceof BlockStatement) {
            visit((BlockStatement) a);
        } else if (a instanceof AssertStatement) {
            visit((AssertStatement) a);
        } else if (a instanceof ContinueStatement) {
            visit((ContinueStatement) a);
        } else if (a instanceof ExpressionStatement) {
            visit((ExpressionStatement) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(StaticOnDemandImportDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(SwitchBlockStatementGroup a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getStatements();
        if (o != null) {
            for (Statement l : (List<Statement>) o) {
                visit((Statement) l);
            }
        }
        o = a.getLabels();
        if (o != null) {
            for (Expression l : (List<Expression>) o) {
                visit((Expression) l);
            }
        }
    }

    public void visit(SwitchStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getSelector();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getEntries();
        if (o != null) {
            for (SwitchBlockStatementGroup l : (List<SwitchBlockStatementGroup>) o) {
                visit((SwitchBlockStatementGroup) l);
            }
        }
    }

    public void visit(SynchronizedStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(ThisExpression a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(ThrowStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExpression();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(TryStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTryBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
        o = a.getCatchClauses();
        if (o != null) {
            for (CatchClause l : (List<CatchClause>) o) {
                visit((CatchClause) l);
            }
        }
        o = a.getFinallyBlock();
        if (o != null) {
            visit((BlockStatement) o);
        }
    }

    public void visit(Type a) {
        if (a == null) {
            return;
        } else if (a instanceof PrimitiveType) {
            visit((PrimitiveType) a);
        } else if (a instanceof ReferenceType) {
            visit((ReferenceType) a);
        } else if (a instanceof VoidType) {
            visit((VoidType) a);
        } else if (a instanceof WildcardType) {
            visit((WildcardType) a);
        } else if (a instanceof ArrayType) {
            visit((ArrayType) a);
        } else if (a instanceof ClassOrInterfaceType) {
            visit((ClassOrInterfaceType) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(TypeArguments a) {
        if (a == null) {
            return;
        } else if (a instanceof NonWildTypeArguments) {
            visit((NonWildTypeArguments) a);
        }
        Object o = null;
        o = a.getTypes();
        if (o != null) {
            for (Type l : (List<Type>) o) {
                visit((Type) l);
            }
        }
    }

    public void visit(TypeBody a) {
        if (a == null) {
            return;
        } else if (a instanceof ClassOrInterfaceBody) {
            visit((ClassOrInterfaceBody) a);
        } else if (a instanceof AnnotationTypeBody) {
            visit((AnnotationTypeBody) a);
        } else if (a instanceof EnumBody) {
            visit((EnumBody) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(TypeDeclaration a) {
        if (a == null) {
            return;
        } else if (a instanceof ClassOrInterfaceDeclaration) {
            visit((ClassOrInterfaceDeclaration) a);
        } else if (a instanceof EnumDeclaration) {
            visit((EnumDeclaration) a);
        } else if (a instanceof AnnotationTypeDeclaration) {
            visit((AnnotationTypeDeclaration) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(TypeOnDemandImportDeclaration a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((QualifiedName) o);
        }
    }

    public void visit(TypeParameter a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getTypesBound();
        if (o != null) {
            for (ClassOrInterfaceType l : (List<ClassOrInterfaceType>) o) {
                visit((ClassOrInterfaceType) l);
            }
        }
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(UnaryExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof SignedExpression) {
            visit((SignedExpression) a);
        } else if (a instanceof UnaryNotPlusMinusExpression) {
            visit((UnaryNotPlusMinusExpression) a);
        } else if (a instanceof PrefixIncrementExpression) {
            visit((PrefixIncrementExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(UnaryNotPlusMinusExpression a) {
        if (a == null) {
            return;
        } else if (a instanceof PostfixExpression) {
            visit((PostfixExpression) a);
        } else if (a instanceof CastExpression) {
            visit((CastExpression) a);
        } else if (a instanceof NegatedExpression) {
            visit((NegatedExpression) a);
        } else {
            throw new RuntimeException("Unknown type: " + a.getClass());
        }
    }

    public void visit(VariableDeclarator a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getInit();
        if (o != null) {
            visit((Expression) o);
        }
        o = a.getId();
        if (o != null) {
            visit((VariableDeclaratorId) o);
        }
    }

    public void visit(VariableDeclaratorId a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getName();
        if (o != null) {
            visit((Identifier) o);
        }
    }

    public void visit(VoidType a) {
        if (a == null) {
            return;
        }
        Object o = null;
    }

    public void visit(WhileNoShortIfStatement a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getBody();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(WhileStatement a) {
        if (a == null) {
            return;
        } else if (a instanceof WhileNoShortIfStatement) {
            visit((WhileNoShortIfStatement) a);
        }
        Object o = null;
        o = a.getBody();
        if (o != null) {
            visit((Statement) o);
        }
        o = a.getCondition();
        if (o != null) {
            visit((Expression) o);
        }
    }

    public void visit(WhiteSpace a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getType();
        if (o != null) {
            visit((WhiteSpaceType) o);
        }
    }

    public void visit(WhiteSpaceType a) {
        //do nothing with enum
    }

    public void visit(WildcardType a) {
        if (a == null) {
            return;
        }
        Object o = null;
        o = a.getExtendsType();
        if (o != null) {
            visit((ReferenceType) o);
        }
        o = a.getSuperType();
        if (o != null) {
            visit((ReferenceType) o);
        }
    }
}
