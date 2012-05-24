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

}
