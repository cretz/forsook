package org.forsook.parser;

import java.math.BigDecimal;
import java.util.Set;

public class ParseletInstance implements Comparable<ParseletInstance> {

    private final String name;
    private final Set<Class<?>> emits;
    private final Set<Class<?>> needs;
    private final Set<Class<? extends Parselet<?>>> replaces;
    private final BigDecimal precedence;
    private final Parselet<?> parselet;
    
    ParseletInstance(String name, Set<Class<?>> emits, Set<Class<?>> needs,
            Set<Class<? extends Parselet<?>>> replaces, BigDecimal precedence,
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

    Set<Class<?>> getEmits() {
        return emits;
    }
    
    Set<Class<?>> getNeeds() {
        return needs;
    }
    
    Set<Class<? extends Parselet<?>>> getReplaces() {
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
