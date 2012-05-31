package org.forsook.parser;

import java.math.BigDecimal;
import java.util.List;

public class ParseletInstance implements Comparable<ParseletInstance> {

    private final String name;
    private final List<Class<?>> emits;
    private final List<Class<?>> needs;
    private final List<Class<? extends Parselet<?>>> replaces;
    private final BigDecimal precedence;
    private final Parselet<?> parselet;
    
    ParseletInstance(String name, List<Class<?>> emits, List<Class<?>> needs,
            List<Class<? extends Parselet<?>>> replaces, BigDecimal precedence,
            Parselet<?> parselet) {
        this.name = name;
        this.emits = emits;
        this.needs = needs;
        this.replaces = replaces;
        this.precedence = precedence;
        this.parselet = parselet;
    }

    String getName() {
        return name;
    }

    List<Class<?>> getEmits() {
        return emits;
    }
    
    List<Class<?>> getNeeds() {
        return needs;
    }
    
    List<Class<? extends Parselet<?>>> getReplaces() {
        return replaces;
    }

    BigDecimal getPrecedence() {
        return precedence;
    }

    Parselet<?> getParselet() {
        return parselet;
    }

    @Override
    public int compareTo(ParseletInstance o) {
        int compare = precedence.compareTo(o.precedence);
        return compare != 0 ? compare : name.compareTo(o.name);
    }
}
