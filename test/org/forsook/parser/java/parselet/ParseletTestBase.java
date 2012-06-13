package org.forsook.parser.java.parselet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.ServiceLoader;

import junit.framework.Assert;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.ParserUtils;
import org.forsook.parser.SimpleParser;
import org.forsook.parser.java.JavaSourceWriter;
import org.forsook.parser.java.ast.name.QualifiedName;

public abstract class ParseletTestBase {
    
    private static final Properties allLocalParseletsEnabled = new Properties();
    
    static {
        for (Parselet<?> parselet : ServiceLoader.load(Parselet.class)) {
            ParseletDefinition definition = parselet.getClass().getAnnotation(ParseletDefinition.class);
            if (definition != null && !definition.name().isEmpty()) {
                allLocalParseletsEnabled.setProperty(definition.name() + ".enabled", "true");
            }
        }
    }
    
    protected static String streamToString(InputStream stream) {
        Reader reader = new InputStreamReader(stream);
        try {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (Exception e) { }
        }
    }

    protected Parser buildParser(String source) {
        return new SimpleParser(source, ParserUtils.buildParseletMap(null));
    }
    
    protected void assertParse(String source, Object expected) {
        assertParse(source, expected.getClass(), expected);
    }
    
    protected void assertNull(String source, Class<?> classToEmit) {
        assertParse(source, classToEmit, null);
    }
    
    protected void assertParse(String source, Class<?> classToEmit, Object expected) {
        Object actual = buildParser(source).next(classToEmit);
        Assert.assertEquals("Results unequal for class: " + classToEmit, expected, actual);
    }
    
    protected void assertString(String source, Class<?> classToEmit) {
        Object actual = buildParser(source).next(classToEmit);
        Assert.assertNotNull("Result null for class: " + classToEmit, actual);
        Assert.assertEquals(source, new JavaSourceWriter().build(actual).toString());
    }
    
    protected QualifiedName getQualifiedName(String value) {
        return buildParser(value).next(QualifiedName.class);
    }
}
