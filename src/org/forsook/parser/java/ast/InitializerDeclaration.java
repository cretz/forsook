package org.forsook.parser.java.ast;

import org.forsook.parser.java.JlsReference;

@JlsReference({ "8.6", "8.7" })
@SuppressWarnings("serial")
public class InitializerDeclaration extends BodyDeclaration {

    private boolean _static;
    private BlockStatement block;
    
    public InitializerDeclaration() {
    }
    
    public InitializerDeclaration(boolean _static, BlockStatement block) {
        this._static = _static;
        this.block = block;
    }
    
    public boolean isStatic() {
        return _static;
    }
    
    public void setStatic(boolean _static) {
        this._static = _static;
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
        int result = super.hashCode();
        result = prime * result + (_static ? 1231 : 1237);
        result = prime * result + ((block == null) ? 0 : block.hashCode());
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
        InitializerDeclaration other = (InitializerDeclaration) obj;
        if (_static != other._static) {
            return false;
        }
        if (block == null) {
            if (other.block != null) {
                return false;
            }
        } else if (!block.equals(other.block)) {
            return false;
        }
        return true;
    }
}
