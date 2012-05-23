package org.forsook.parser.java.parselet;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.ImportDeclaration;

@ParseletDepends({
	WhiteSpaceParselet.class,
	CommentParselet.class,
	QualifiedNameParselet.class
})
public class ImportDeclarationParselet implements Parselet<ImportDeclaration> {

	@Override
	@SuppressWarnings("unchecked")
	public ImportDeclaration parse(Parser parser) {
		//needs "import" to be present
		if (!parser.peekPresentAndSkip("import")) {
			return null;
		}
		//whitespace required here
		if (parser.any(WhiteSpaceParselet.class, CommentParselet.class).isEmpty()) {
		    return null;
		}
		//static present?
		boolean _static = parser.peekPresentAndSkip("static");
		//more whitespace?
		if (_static) {
		    //required
		    if (parser.any(WhiteSpaceParselet.class, CommentParselet.class).isEmpty()) {
		        return null;
		    }
		}
		//get name
		String name = parser.next(QualifiedNameParselet.class);
		boolean asterisk = false;
		if (name.endsWith(".")) {
		    if (!parser.peekPresentAndSkip('*')) {
		        return null;
		    }
		    asterisk = true;
		}
		//return
		return new ImportDeclaration(name, _static, asterisk);
	}

}
