package org.forsook.parser.java.ast;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class NormalAnnotationExpression extends AnnotationExpression {
    
    private List<ElementValuePair> pairs;
    
    public NormalAnnotationExpression() {
        
    }
    
    public NormalAnnotationExpression(String name, List<ElementValuePair> pairs) {
        super(name);
        this.pairs = pairs;
    }
    
    public List<ElementValuePair> getPairs() {
        return pairs;
    }
    
    public void setPairs(List<ElementValuePair> pairs) {
        this.pairs = pairs;
    }

    public static class ElementValuePair implements Serializable {
        
        private String name;
        private Expression value;
        
        public ElementValuePair() {
        }
         
        public ElementValuePair(String name, Expression value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Expression getValue() {
            return value;
        }
        
        public void setValue(Expression value) {
            this.value = value;
        }
    }
}
