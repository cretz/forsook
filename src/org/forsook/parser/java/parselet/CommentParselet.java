package org.forsook.parser.java.parselet;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.BlockComment;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.LineComment;


public class CommentParselet implements Parselet<Comment> {

	@Override
	public Comment parse(Parser parser) {
		if (parser.peekPresentAndSkip("//")) {
		    //read until new line
		    StringBuilder string = new StringBuilder();
		    Character chr;
		    do {
		        chr = parser.peek();
		        if (chr == null || chr == '\r' || chr == '\n') {
		            break;
		        } else {
		            string.append(chr);
		            parser.skip(1);
		        }
		    } while (true);
		    //skip the newline if present
		    if (chr != null) {
		        parser.skip(1);
		        //skip the extra char if present
		        if (chr == '\r') {
		            parser.peekPresentAndSkip('\n');
		        }
		    }
		    return new LineComment(string.toString());
		} else if (parser.peekPresentAndSkip("/*")) {
		    //read until end
		    StringBuilder string = new StringBuilder();
		    boolean prevWasAsterisk = false;
		    Character chr;
		    do {
		        chr = parser.peek();
		        if (chr == null) {
		            return null;
		        } else {
		            parser.skip(1);
		            if (prevWasAsterisk) {
    		            if (chr == '/') {
    		                break;
    		            } else {
                            string.append('*');
    		                prevWasAsterisk = false;
    		            }
		            }
		            if (chr == '*') {
		                prevWasAsterisk = true;
		            } else {
		                string.append(chr);
		            }
		        }
		    } while (true);
		    if (string.length() > 0 && string.charAt(0) == '*') {
		        return new JavadocComment(string.substring(1).toString());
		    } else {
		        return new BlockComment(string.toString());
		    }
		} else {
		    return null;
		}
	}
	
}
