package org.forsook.parser.java.ast.decl;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.Expression;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.lexical.Identifier;
import org.forsook.parser.java.ast.name.QualifiedName;

@JlsReference("9.7.1")
@SuppressWarnings("serial")
public class NormalAnnotationExpression extends AnnotationExpression {
    
    private List<ElementValuePair> pairs;
    
    public NormalAnnotationExpression() {
        
    }
    
    public NormalAnnotationExpression(QualifiedName name, List<ElementValuePair> pairs) {
        super(name);
        this.pairs = pairs;
    }
    
    public List<ElementValuePair> getPairs() {
        return pairs;
    }
    
    public void setPairs(List<ElementValuePair> pairs) {
        this.pairs = pairs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((pairs == null) ? 0 : pairs.hashCode());
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
        NormalAnnotationExpression other = (NormalAnnotationExpression) obj;
        if (pairs == null) {
            if (other.pairs != null) {
                return false;
            }
        } else if (!pairs.equals(other.pairs)) {
            return false;
        }
        return true;
    }

    public static class ElementValuePair extends JavaModel {
        
        private Identifier name;
        private Expression value;
        
        public ElementValuePair() {
        }
         
        public ElementValuePair(Identifier name, Expression value) {
            this.name = name;
            this.value = value;
        }
        
        public Identifier getName() {
            return name;
        }
        
        public void setName(Identifier name) {
            this.name = name;
        }
        
        public Expression getValue() {
            return value;
        }
        
        public void setValue(Expression value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ElementValuePair other = (ElementValuePair) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else if (!value.equals(other.value)) {
                return false;
            }
            return true;
        }
    }
}
