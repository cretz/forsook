package org.forsook.parser.java.parselet;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;

public class QualifiedNameParselet implements Parselet<String> {

	@Override
	public String parse(Parser parser) {
		//simply put, either an identifier char or a dot
		StringBuilder builder = new StringBuilder();
		boolean valid;
		do {
			valid = false;
			Character chr = parser.peek();
			if (chr != null) {
				if (builder.length() == 0 && Character.isJavaIdentifierStart(chr)) {
					valid = true;
				} else if (builder.length() > 0 && 
						(chr == '.' || Character.isJavaIdentifierPart(chr))) {
					valid = true;
				}
				if (valid) {
					builder.append(chr);
					parser.skip(1);
				}
			}
		} while (valid);
		if (builder.length() == 0) {
			return null;
		} else {
			return builder.toString();
		}
	}

}
