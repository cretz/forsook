package org.forsook.parser;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

public class ParserUtils {
    
    public static Map<Class<?>, NavigableSet<ParseletInstance>> buildParseletMap(
            Properties properties) {
        Map<Class<? extends Parselet<?>>, ParseletInstance> instances = 
                new HashMap<Class<? extends Parselet<?>>, ParseletInstance>();
        //loop through to create instances
        for (Parselet<?> parselet : ServiceLoader.load(Parselet.class)) {
            applyInstance(parselet, properties, instances);
        }
        //now loop through the instances making the final map by what it emits
        Map<Class<?>, NavigableSet<ParseletInstance>> map = 
                new HashMap<Class<?>, NavigableSet<ParseletInstance>>();
        for (ParseletInstance instance : instances.values()) {
            for (Class<?> emit : instance.getEmits()) {
                NavigableSet<ParseletInstance> instanceSet = map.get(emit);
                if (instanceSet == null) {
                    instanceSet = new TreeSet<ParseletInstance>();
                    map.put(emit, instanceSet);
                }
                instanceSet.add(instance);
            }
        }
        //validate all needs
        for (ParseletInstance instance : instances.values()) {
            for (Class<?> need : instance.getNeeds()) {
                if (!map.containsKey(need)) {
                    throw new RuntimeException("Parselet " + instance.getParselet().getClass() + 
                            " needs type " + need + ", but no parselets emits that type");
                }
            }
        }
        return map;
    }
    
    @SuppressWarnings("unchecked")
    private static void applyInstance(Parselet<?> parselet, Properties properties,
            Map<Class<? extends Parselet<?>>, ParseletInstance> instances) {
        //get definition...
        ParseletDefinition definition = parselet.getClass().getAnnotation(ParseletDefinition.class);
        if (definition == null) {
            throw new RuntimeException("ParseletDefinition required for " + parselet.getClass());
        } else if (properties != null && !"true".equals(
                properties.getProperty(definition.name() + ".enabled"))) {
            //gotta be enabled
            return;
        }
        BigDecimal precedence;
        try {
            precedence = new BigDecimal(definition.precedence());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid precedence format on ParseletDefinition for " + parselet.getClass(), e);
        }
        //validation
        if (!Modifier.isAbstract(parselet.getClass().getModifiers())) {
            if (definition.name().isEmpty()) {
                throw new RuntimeException("ParseletDefinition name required for " + parselet.getClass());
            } else if (definition.emits().length == 0) {
                throw new RuntimeException("ParseletDefinition emits required for " + parselet.getClass());
            }
        }
        //emits
        Set<Class<?>> emits = new HashSet<Class<?>>(Arrays.asList(definition.emits()));
        //needs
        Set<Class<?>> needs = new HashSet<Class<?>>();
        Class<?> superClass = parselet.getClass();
        do {
            ParseletDefinition superDefinition = superClass.getAnnotation(ParseletDefinition.class);
            needs.addAll(Arrays.asList(superDefinition.needs()));
            superClass = superClass.getSuperclass();
        } while (superClass.isAnnotationPresent(ParseletDefinition.class));
        //build instance
        ParseletInstance instance = new ParseletInstance(definition.name(), emits, needs, 
                new HashSet<Class<? extends Parselet<?>>>(Arrays.asList(definition.replaces())), 
                precedence, parselet, definition.recursiveMinimumSize());
        if (!instance.getReplaces().isEmpty()) {
            //go replacing...
            for (Class<? extends Parselet<?>> replace : instance.getReplaces()) {
                //grab what's there
                ParseletInstance previous = instances.get(replace);
                //must either be not present, be the thing we're replacing exactly, or
                //  have a higher precedence number to replace
                if (previous == null || previous.getParselet().getClass().equals(replace) ||
                        previous.getPrecedence().compareTo(instance.getPrecedence()) > 0) {
                    instances.put(replace, instance);
                }
            }
        } else {
            //can't override a replaced one no matter the precedence
            if (!instances.containsKey(parselet.getClass())) {
                instances.put((Class<? extends Parselet<?>>) parselet.getClass(), instance);
            }
        }
    }

    private ParserUtils() {
    }
}
