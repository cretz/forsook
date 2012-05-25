package org.forsook.parser;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParserUtils {
    
    public static Set<Class<? extends Parselet<?>>> getDependencies(Class<? extends Parselet<?>>... parseletClasses) {
        Set<Class<? extends Parselet<?>>> dependencies = new HashSet<Class<? extends Parselet<?>>>();
        for (Class<? extends Parselet<?>> parseletClass : parseletClasses) {
            populateDependencies(dependencies, parseletClass);
        }
        return dependencies;
    }
    
    @SuppressWarnings("unchecked")
    private static void populateDependencies(Set<Class<? extends Parselet<?>>> set, 
            Class<? extends Parselet<?>> parseletClass) {
        if (!set.contains(parseletClass)) {
            set.add(parseletClass);
            ParseletDepends depends = parseletClass.getAnnotation(ParseletDepends.class);
            if (depends != null) {
                for (Class<? extends Parselet<?>> parseletChild : depends.value()) {
                    populateDependencies(set, parseletChild);
                }
            }
            //also do super
            if (Parselet.class.isAssignableFrom(parseletClass.getSuperclass())) {
                populateDependencies(set, (Class<? extends Parselet<?>>) parseletClass.getSuperclass());
            }
        }
    }
    
    public static Map<Class<? extends Parselet<?>>, Parselet<?>> buildParseletMap(
            Set<Class<? extends Parselet<?>>> parseletClassesWithDependencies) {
        Map<Class<? extends Parselet<?>>, Parselet<?>> map = 
                new HashMap<Class<? extends Parselet<?>>, Parselet<?>>(parseletClassesWithDependencies.size());
        try {
            for (Class<? extends Parselet<?>> parseletClass : parseletClassesWithDependencies) {
                //can't be abstract
                if (!Modifier.isAbstract(parseletClass.getModifiers())) {
                    map.put(parseletClass, parseletClass.newInstance());
                }
            }
        } catch (InstantiationException e) {
           throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private ParserUtils() {
    }
}
