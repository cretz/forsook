package org.forsook.parser.java;

import java.io.File;
import java.util.Map;
import java.util.NavigableSet;

import org.forsook.parser.ParseletInstance;
import org.forsook.parser.Parser;
import org.forsook.parser.ParserUtils;
import org.forsook.parser.SimpleParser;
import org.forsook.parser.java.ast.packag.CompilationUnit;
import org.forsook.util.IoUtils;

public class DirectoryParseCheck {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Need dir arg");
        }
        File dir = new File(args[0]);
        if (!dir.isDirectory()) {
            throw new RuntimeException("Invalid dir: " + args[0]);
        }
        Map<Class<?>, NavigableSet<ParseletInstance>> parseletMap = 
                ParserUtils.buildParseletMap(null);
        checkAndParseFiles(dir, parseletMap);
    }

    private static void checkAndParseFiles(File dir,
            Map<Class<?>, NavigableSet<ParseletInstance>> parseletMap) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                checkAndParseFiles(file, parseletMap);
            } else if (file.getName().endsWith(".java")) {
                System.out.print("Checking " + file + "... ");
                long ms = System.currentTimeMillis();
                Parser parser = new SimpleParser(IoUtils.
                        fileToString(file), parseletMap);
                CompilationUnit unit = parser.next(CompilationUnit.class);
                System.out.print((System.currentTimeMillis() - ms) + "ms ");
                if (!parser.isPeekEndOfInput()) {
                    System.out.println("fail");
                    System.out.println(new JavaSourceWriter().build(unit));
                    System.exit(1);
                } else {
                    System.out.println("success");
                }
            }
        }
    }

}
