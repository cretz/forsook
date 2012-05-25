package org.forsook.parser.java.ast;

@SuppressWarnings("serial")
public class ImportDeclaration extends JavaModel {

    private String name;
    private boolean _static;
    private boolean asterisk;

    public ImportDeclaration() {
    }

    public ImportDeclaration(String name, boolean _static, boolean asterisk) {
        this.name = name;
        this._static = _static;
        this.asterisk = asterisk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatic() {
        return _static;
    }

    public void setStatic(boolean _static) {
        this._static = _static;
    }

    public boolean isAsterisk() {
        return asterisk;
    }

    public void setAsterisk(boolean asterisk) {
        this.asterisk = asterisk;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (_static ? 1231 : 1237);
        result = prime * result + (asterisk ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        ImportDeclaration other = (ImportDeclaration) obj;
        if (_static != other._static) {
            return false;
        }
        if (asterisk != other.asterisk) {
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
