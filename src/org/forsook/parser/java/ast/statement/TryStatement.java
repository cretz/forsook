package org.forsook.parser.java.ast.statement;

import java.util.List;

import org.forsook.parser.java.JlsReference;

@JlsReference("14.20")
@SuppressWarnings("serial")
public class TryStatement extends Statement {

    private BlockStatement tryBlock;
    private List<CatchClause> catchClauses;
    private BlockStatement finallyBlock;
    
    public TryStatement() {
    }

    public TryStatement(BlockStatement tryBlock,
            List<CatchClause> catchClauses, BlockStatement finallyBlock) {
        this.tryBlock = tryBlock;
        this.catchClauses = catchClauses;
        this.finallyBlock = finallyBlock;
    }

    public BlockStatement getTryBlock() {
        return tryBlock;
    }

    public void setTryBlock(BlockStatement tryBlock) {
        this.tryBlock = tryBlock;
    }

    public List<CatchClause> getCatchClauses() {
        return catchClauses;
    }

    public void setCatchClauses(List<CatchClause> catchClauses) {
        this.catchClauses = catchClauses;
    }

    public BlockStatement getFinallyBlock() {
        return finallyBlock;
    }

    public void setFinallyBlock(BlockStatement finallyBlock) {
        this.finallyBlock = finallyBlock;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((catchClauses == null) ? 0 : catchClauses.hashCode());
        result = prime * result
                + ((finallyBlock == null) ? 0 : finallyBlock.hashCode());
        result = prime * result
                + ((tryBlock == null) ? 0 : tryBlock.hashCode());
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
        TryStatement other = (TryStatement) obj;
        if (catchClauses == null) {
            if (other.catchClauses != null) {
                return false;
            }
        } else if (!catchClauses.equals(other.catchClauses)) {
            return false;
        }
        if (finallyBlock == null) {
            if (other.finallyBlock != null) {
                return false;
            }
        } else if (!finallyBlock.equals(other.finallyBlock)) {
            return false;
        }
        if (tryBlock == null) {
            if (other.tryBlock != null) {
                return false;
            }
        } else if (!tryBlock.equals(other.tryBlock)) {
            return false;
        }
        return true;
    }
}
