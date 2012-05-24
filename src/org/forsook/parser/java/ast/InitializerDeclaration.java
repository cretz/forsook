package org.forsook.parser.java.ast;


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
}
