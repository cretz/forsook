package org.forsook.parser.java.parselet;

import java.util.Properties;
import java.util.ServiceLoader;

import junit.framework.Assert;

import org.forsook.parser.Parselet;
import org.forsook.parser.ParseletDefinition;
import org.forsook.parser.Parser;
import org.forsook.parser.ParserUtils;
import org.forsook.parser.SimpleParser;

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

    protected Parser buildParser(String source) {
        return new SimpleParser(source, ParserUtils.buildParseletMap(allLocalParseletsEnabled));
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
}
