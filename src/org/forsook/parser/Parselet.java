package org.forsook.parser;

/**
 * Base interface for all parselets. Besides implementing this interface,
 * all parselets should also contain the {@link ParseletDefinition} annotation.
 *
 * @param <T>
 *
 * @author Chad Retz
 */
public interface Parselet<T> {

    /**
     * Using the given parser, parse a value out
     * 
     * @param parser
     * @return
     */
    T parse(Parser parser);
}
