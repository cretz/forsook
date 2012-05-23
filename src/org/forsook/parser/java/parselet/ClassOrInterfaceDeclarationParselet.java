package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.JavadocComment;

public class ClassOrInterfaceDeclarationParselet 
		extends TypeDeclarationParselet<ClassOrInterfaceDeclaration> {

	
	
	@Override
	@SuppressWarnings("unchecked")
	public ClassOrInterfaceDeclaration parse(Parser parser) {
		//annotations, javadoc, and modifiers
		JavadocComment javadoc = null;
		List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
		int modifiers = 0;
		for (Object found : parser.any(WhiteSpaceParselet.class, CommentParselet.class,
				AnnotationExpressionParselet.class, ModifierParselet.class)) {
			if (found instanceof JavadocComment) {
				javadoc = (JavadocComment) found;
			} else if (found instanceof AnnotationExpression) {
				annotations.add((AnnotationExpression) found);
			} else if (found instanceof Integer) {
			    modifiers &= (Integer) found;
			}
		}
		//interface/class
		boolean iface = parser.peekPresentAndSkip("interface");
		if (!iface && !parser.peekPresentAndSkip("class")) {
		    return null;
		}
		//some spacing required
		if (parser.any(WhiteSpaceParselet.class, CommentParselet.class).isEmpty()) {
		    return null;
		}
		//name
		String name = parser.next(IdentifierParselet.class);
		if (name == null) {
		    return null;
		}
		//TODO: type parameters
		
		return null;
	}

}
