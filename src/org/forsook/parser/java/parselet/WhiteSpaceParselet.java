package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;

public class WhiteSpaceParselet extends JavaParselet<List<Character>> {

	@Override
	public List<Character> parse(Parser parser) {
	    List<Character> chars = new ArrayList<Character>();
	    Character chr;
	    do {
	        chr = parser.peek();
	        if (chr != null && Character.isWhitespace(chr)) {
	            parser.next();
	            chars.add(chr);
	        } else {
	            chr = null;
	        }
	    } while (chr != null);
	    if (chars.isEmpty()) {
	        return null;
	    } else {
	        return chars;
	    }
	}

}
