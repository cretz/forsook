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
    private final Integer recursiveMinimumSize;
    private long totalMs;
    private int totalCount;
    
    ParseletInstance(String name, Set<Class<?>> emits, Set<Class<?>> needs,
            Set<Class<? extends Parselet<?>>> replaces, BigDecimal precedence,
            Parselet<?> parselet, int recursiveMinimumSize) {
        this.name = name;
        this.emits = emits;
        this.needs = needs;
        this.replaces = replaces;
        this.precedence = precedence;
        this.parselet = parselet;
        this.recursiveMinimumSize = recursiveMinimumSize > 0 ? recursiveMinimumSize : null;
    }

    public String getName() {
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

    public Parselet<?> getParselet() {
        return parselet;
    }
    
    Integer getRecursiveMinimumSize() {
        return recursiveMinimumSize;
    }

    @Override
    public int compareTo(ParseletInstance o) {
        int compare = precedence.compareTo(o.precedence);
        return compare != 0 ? compare : name.compareTo(o.name);
    }
    
    void incrementTotalCount(long ms) {
        totalCount++;
        totalMs += ms;
    }
    
    public int getTotalCount() {
        return totalCount;
    }
    
    public long getTotalMs() {
        return totalMs;
    }
}
