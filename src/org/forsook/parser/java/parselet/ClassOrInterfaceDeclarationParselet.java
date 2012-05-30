package org.forsook.parser.java.parselet;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.AnnotationExpression;
import org.forsook.parser.java.ast.ClassOrInterfaceDeclaration;
import org.forsook.parser.java.ast.ClassOrInterfaceType;
import org.forsook.parser.java.ast.Comment;
import org.forsook.parser.java.ast.Identifier;
import org.forsook.parser.java.ast.JavadocComment;
import org.forsook.parser.java.ast.Modifier;
import org.forsook.parser.java.ast.TypeParameter;
import org.forsook.parser.java.ast.WhiteSpace;

@JlsReference({ "8.1", "9.1" })
@ParseletDefinition(
        name = "forsook.java.classOrInterfaceDeclaration",
        emits = ClassOrInterfaceDeclaration.class,
        needs = {
            WhiteSpace.class,
            Comment.class,
            AnnotationExpression.class,
            Modifier.class,
            Identifier.class,
            ClassOrInterfaceType.class
        }
)
public class ClassOrInterfaceDeclarationParselet extends TypeDeclarationParselet<ClassOrInterfaceDeclaration> {
    
    @Override
    public ClassOrInterfaceDeclaration parse(Parser parser) {
        //annotations, javadoc, and modifiers
        JavadocComment javadoc = null;
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        int modifiers = 0;
        for (Object found : parser.any(WhiteSpace.class, Comment.class, 
                AnnotationExpression.class, Modifier.class)) {
            if (found instanceof JavadocComment) {
                javadoc = (JavadocComment) found;
            } else if (found instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) found);
            } else if (found instanceof Modifier) {
                modifiers &= ((Modifier) found).getModifier();
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
        Identifier name = parser.next(Identifier.class);
        if (name == null) {
            return null;
        }
        parseWhiteSpaceAndComments(parser);
        //type parameters
        List<TypeParameter> parameters = parseTypeParameters(parser);
        parseWhiteSpaceAndComments(parser);
        //extends
        List<ClassOrInterfaceType> extendsList = new ArrayList<ClassOrInterfaceType>();
        if (parser.peekPresentAndSkip("extends")) {
            ClassOrInterfaceType type;
            do {
                parseWhiteSpaceAndComments(parser);
                type = parser.next(ClassOrInterfaceType.class);
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
                type = parser.next(ClassOrInterfaceType.class);
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
