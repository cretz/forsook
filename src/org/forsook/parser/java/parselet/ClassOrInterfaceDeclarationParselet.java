package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.TypeParameter;

public class ClassOrInterfaceDeclarationParselet extends TypeDeclarationParselet<ClassOrInterfaceDeclaration> {
	
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
		if (parseWhiteSpaceAndComments(parser).isEmpty()) {
		    return null;
		}
		//name
		String name = parser.next(IdentifierParselet.class);
		if (name == null) {
		    return null;
		}
		parser.any(WhiteSpaceParselet.class, CommentParselet.class);
		//type parameters
		List<TypeParameter> parameters = parseTypeParameters(parser);
        parser.any(WhiteSpaceParselet.class, CommentParselet.class);
		//extends
		List<ClassOrInterfaceType> extendsList = new ArrayList<ClassOrInterfaceType>();
		if (parser.peekPresentAndSkip("extends")) {
		    ClassOrInterfaceType type;
	        do {
	            parseWhiteSpaceAndComments(parser);
	            type = parser.next(ClassOrInterfaceTypeParselet.class);
	            if (type != null) {
	                extendsList.add(type);
	                parseWhiteSpaceAndComments(parser);
	            }
	        } while (type != null && parser.peekPresentAndSkip(','));
	        if (extendsList.isEmpty()) {
	            return null;
	        }
		}
		//implements
        List<ClassOrInterfaceType> implementsList = new ArrayList<ClassOrInterfaceType>();
        if (parser.peekPresentAndSkip("implements")) {
            ClassOrInterfaceType type;
            do {
                parseWhiteSpaceAndComments(parser);
                type = parser.next(ClassOrInterfaceTypeParselet.class);
                if (type != null) {
                    implementsList.add(type);
                    parseWhiteSpaceAndComments(parser);
                }
            } while (type != null && parser.peekPresentAndSkip(','));
            if (implementsList.isEmpty()) {
                return null;
            }
        }
		//TODO: members
		return null;
	}

}
