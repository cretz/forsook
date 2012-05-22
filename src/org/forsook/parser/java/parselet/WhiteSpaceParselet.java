package org.forsook.parser.java.parselet;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;

public class WhiteSpaceParselet implements Parselet<Void> {

	@Override
	public Void parse(Parser parser) {
		while (Character.isWhitespace(parser.peek())) {
			parser.next();
		}
		return null;
	}

}
