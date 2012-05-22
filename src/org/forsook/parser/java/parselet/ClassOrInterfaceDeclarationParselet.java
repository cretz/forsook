package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;

public class ClassOrInterfaceDeclarationParselet 
		extends TypeDeclarationParselet<ClassOrInterfaceDeclaration> {

	
	
	@Override
	@SuppressWarnings("unchecked")
	public ClassOrInterfaceDeclaration parse(Parser parser) {
		//annotations and javadoc
		String javadoc = null;
		List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
		for (Object found : parser.any(WhiteSpaceParselet.class, BlockCommentParselet.class,
				AnnotationExpressionParselet.class)) {
			if (found instanceof String && ((String) found).startsWith("/**")) {
				javadoc = (String) found;
			} else if (found instanceof AnnotationExpression) {
				annotations.add((AnnotationExpression) found);
			}
		}
		//modifiers
		//TODO
		return null;
	}

}
