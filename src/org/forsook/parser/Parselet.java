package org.forsook.parser;


public interface Parselet<T> {

	T parse(Parser parser);
}
