package org.forsook.parser.java.parselet.decl;

import java.util.ArrayList;
import java.util.List;

import org.forsook.parser.Parser;
import org.forsook.parser.java.ast.decl.BodyDeclaration;
import org.forsook.parser.java.ast.decl.TypeBody;
import org.forsook.parser.java.parselet.JavaParselet;

public abstract class TypeBodyParselet<T extends TypeBody> extends JavaParselet<T> {
        
    @SuppressWarnings("unchecked")
    protected List<BodyDeclaration> parseTypeMembers(Parser parser, boolean checkBraces,
            Class<? extends BodyDeclaration>... classes) {
        //brace
        if (checkBraces) {
            if (!parser.peekPresentAndSkip('{')) {
                return null;
            }
            //lookahead
            if (!parser.pushFirstDepthLookAhead(parser.getAstDepth(), '}')) {
                return null;
            }
        }
        //members
        List<BodyDeclaration> members = new ArrayList<BodyDeclaration>();
        do {
            //no whitespace checking here because it'll gobble up javadoc
            List<BodyDeclaration> memberList = (List<BodyDeclaration>) parser.any(classes);
            if (memberList.isEmpty()) {
                //could just be a semicolon hanging out there (we'll ignore it for now)
                //spacing
                parseWhiteSpaceAndComments(parser);
                if (!parser.peekPresentAndSkip(';')) {
                    break;
                }
            } else {
                members.addAll(memberList);
            }
        } while (true);
        //brace
        if (checkBraces) {
            if (!parser.peekPresentAndSkip('}')) {
                return null;
            }
            //pop lookahead
            parser.popLookAhead();
        }
        return members;
    }
}
