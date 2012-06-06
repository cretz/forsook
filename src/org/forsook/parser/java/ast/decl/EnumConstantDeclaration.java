package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.expression.Expression;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.lexical.JavadocComment;

@JlsReference("8.9.1")
@SuppressWarnings("serial")
public class EnumConstantDeclaration extends BodyDeclaration {

    private Identifier name;
    private List<Expression> arguments;
    private ClassOrInterfaceBody body;
    
    public EnumConstantDeclaration() {
    }

    public EnumConstantDeclaration(JavadocComment javadoc, 
            List<AnnotationExpression> annotations, Identifier name, 
            List<Expression> arguments, ClassOrInterfaceBody body) {
        super(javadoc, annotations);
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    public ClassOrInterfaceBody getBody() {
        return body;
    }
    
    public void setBody(ClassOrInterfaceBody body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((arguments == null) ? 0 : arguments.hashCode());
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EnumConstantDeclaration other = (EnumConstantDeclaration) obj;
        if (arguments == null) {
            if (other.arguments != null) {
                return false;
            }
        } else if (!arguments.equals(other.arguments)) {
            return false;
        }
        if (body == null) {
            if (other.body != null) {
                return false;
            }
        } else if (!body.equals(other.body)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    
}
