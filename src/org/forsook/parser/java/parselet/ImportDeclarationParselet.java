package org.forsook.parser.java.parselet;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.ImportDeclaration;

@ParseletDepends({
	WhiteSpaceParselet.class,
	BlockCommentParselet.class,
	QualifiedNameParselet.class
})
public class ImportDeclarationParselet implements Parselet<ImportDeclaration> {

	@Override
	@SuppressWarnings("unchecked")
	public ImportDeclaration parse(Parser parser) {
		//needs "import" to be present
		if (!parser.peekStringPresent("import")) {
			return null;
		}
		parser.skip(6);
		parser.any(WhiteSpaceParselet.class, BlockCommentParselet.class);
		//static present?
		boolean _static = parser.peekStringPresent("static");
		//must have something here
		if (parser.any(WhiteSpaceParselet.class, BlockCommentParselet.class).isEmpty()) {
			return null;
		}
		//get name
		String name = parser.next(QualifiedNameParselet.class);
		boolean asterisk = false;
		if (name.endsWith(".")) {
			asterisk = parser.peek() == '*';
			if (!asterisk) {
				return null;
			}
			parser.skip(1);
		}
		//return
		return new ImportDeclaration(name, _static, asterisk);
	}

}
