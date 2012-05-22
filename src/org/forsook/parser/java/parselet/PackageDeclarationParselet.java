package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDepends;
import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.PackageDeclaration;

@ParseletDepends({ 
	WhiteSpaceParselet.class, 
	BlockCommentParselet.class, 
	AnnotationExpressionParselet.class,
	QualifiedNameParselet.class })
public class PackageDeclarationParselet implements Parselet<PackageDeclaration> {

	@Override
	@SuppressWarnings("unchecked")
	public PackageDeclaration parse(Parser parser) {
		//hold annotations
		List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
		//loop over whitespace and annotations, only holding on to annotations
		for (Object found : parser.any(WhiteSpaceParselet.class, 
				BlockCommentParselet.class,  AnnotationExpressionParselet.class)) {
			if (found instanceof AnnotationExpression) {
				annotations.add((AnnotationExpression) found);
			}
		}
		//needs "package" to be present
		if (!parser.peekStringPresent("package")) {
			return null;
		}
		parser.skip(7);
		//must have something here
		if (parser.any(WhiteSpaceParselet.class, BlockCommentParselet.class).isEmpty()) {
			return null;
		}
		//get name
		String name = parser.next(QualifiedNameParselet.class);
		//can't end w/ a dot
		if (name.endsWith(".")) {
			return null;
		}
		//could have more mess
		parser.any(WhiteSpaceParselet.class, BlockCommentParselet.class);
		//requires semicolon, then we're good
		if (parser.peek() != ';') {
			return null;
		}
		parser.skip(1);
		//return
		return new PackageDeclaration(annotations, name);
	}

}
