package org.forsook.parser.java.parselet.statement;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.java.JlsReference;
import org.forsook.parser.java.ast.decl.AnnotationExpression;
import org.forsook.parser.java.ast.decl.Modifier;
import org.forsook.parser.java.ast.decl.VariableDeclaratorId;
import org.forsook.parser.java.ast.statement.BlockStatement;
import org.forsook.parser.java.ast.statement.CatchClause;
import org.forsook.parser.java.ast.type.ClassOrInterfaceType;
import org.forsook.parser.java.ast.type.Type;
import org.forsook.parser.java.parselet.JavaParselet;

@JlsReference("14.20")
@ParseletDefinition(
        name = "forsook.java.catchClause",
        emits = CatchClause.class,
        needs = {
            AnnotationExpression.class,
            Modifier.class,
            ClassOrInterfaceType.class,
            VariableDeclaratorId.class,
            BlockStatement.class
        }
)
public class CatchClauseParselet extends JavaParselet<CatchClause> {

    @Override
    public CatchClause parse(Parser parser) {
        //catch
        if (!parser.peekPresentAndSkip("catch")) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip('(')) {
            return null;
        }
        //lookahead
        if (!parser.pushFirstDepthLookAhead(parser.peekAstDepth(), ')')) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //annotations and modifiers
        List<AnnotationExpression> annotations = new ArrayList<AnnotationExpression>();
        boolean finalPresent = false;
        do {
            Object object = parser.first(AnnotationExpression.class, Modifier.class);
            if (object instanceof Modifier) {
                if (((Modifier) object) != Modifier.FINAL || finalPresent) {
                    return null;
                }
                finalPresent = true;
            } else if (object instanceof AnnotationExpression) {
                annotations.add((AnnotationExpression) object);
            } else {
                break;
            }
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (true);
        //types
        List<Type> types = new ArrayList<Type>();
        do {
            Type type = parser.next(ClassOrInterfaceType.class);
            if (type == null) {
                return null;
            }
            types.add(type);
            //spacing
            parseWhiteSpaceAndComments(parser);
        } while (parser.peekPresentAndSkip('|'));
        //name
        VariableDeclaratorId name = parser.next(VariableDeclaratorId.class);
        if (name == null) {
            return null;
        }
        //spacing
        parseWhiteSpaceAndComments(parser);
        //parentheses
        if (!parser.peekPresentAndSkip(')')) {
            return null;
        }
        //pop lookahead
        parser.popLookAhead();
        //spacing
        parseWhiteSpaceAndComments(parser);
        //catch block
        BlockStatement block = parser.next(BlockStatement.class);
        if (block == null) {
            return null;
        }
        return new CatchClause(annotations, finalPresent, types, name, block);
    }

}
