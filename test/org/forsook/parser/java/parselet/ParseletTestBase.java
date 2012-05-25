package org.forsook.parser.java.parselet;

import junit.framework.Assert;

import org.forsook.parser.Parselet;
import org.forsook.parser.Parser;
import org.forsook.parser.ParserUtils;
import org.forsook.parser.SimpleParser;

public abstract class ParseletTestBase {

    protected static Parser buildParser(String source, Class<? extends Parselet<?>>... parseletClasses) {
        return new SimpleParser(source, ParserUtils.buildParseletMap(ParserUtils.getDependencies(parseletClasses)));
    }
    
    @SuppressWarnings("unchecked")
    protected static <U, T extends Parselet<U>> void assertParse(String source, 
            Class<T> parseletClass, U expected) {
        U actual = buildParser(source, parseletClass).next(parseletClass);
        Assert.assertEquals("Results unequal for parselet: " + parseletClass, expected, actual);
    }
}
