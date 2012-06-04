package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.JavaModel;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.type.Type;

@JlsReference("14.20")
@SuppressWarnings("serial")
public class CatchClause extends JavaModel {

    private List<AnnotationExpression> annotations;
    private Modifier modifiers;
    private List<Type> types;
    private VariableDeclaratorId name;
    private BlockStatement block;
    
    public CatchClause() {
    }

    public CatchClause(List<AnnotationExpression> annotations, Modifier modifiers, 
            List<Type> types, VariableDeclaratorId name, BlockStatement block) {
        this.annotations = annotations;
        this.modifiers = modifiers;
        this.types = types;
        this.name = name;
        this.block = block;
    }
    
    public List<AnnotationExpression> getAnnotations() {
        return annotations;
    }
    
    public void setAnnotations(List<AnnotationExpression> annotations) {
        this.annotations = annotations;
    }

    public Modifier getModifiers() {
        return modifiers;
    }

    public void setModifiers(Modifier modifiers) {
        this.modifiers = modifiers;
    }

    public List<Type> getTypes() {
        return types;
    }
    
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public VariableDeclaratorId getName() {
        return name;
    }

    public void setName(VariableDeclaratorId name) {
        this.name = name;
    }

    public BlockStatement getBlock() {
        return block;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result + ((block == null) ? 0 : block.hashCode());
        result = prime * result
                + ((modifiers == null) ? 0 : modifiers.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((types == null) ? 0 : types.hashCode());
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
        CatchClause other = (CatchClause) obj;
        if (annotations == null) {
            if (other.annotations != null) {
                return false;
            }
        } else if (!annotations.equals(other.annotations)) {
            return false;
        }
        if (block == null) {
            if (other.block != null) {
                return false;
            }
        } else if (!block.equals(other.block)) {
            return false;
        }
        if (modifiers == null) {
            if (other.modifiers != null) {
                return false;
            }
        } else if (!modifiers.equals(other.modifiers)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (types == null) {
            if (other.types != null) {
                return false;
            }
        } else if (!types.equals(other.types)) {
            return false;
        }
        return true;
    }
}
