package org.forsook.parser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation required for all parselets that defines their usage.
 *
 * @author Chad Retz
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParseletDefinition {

    /**
     * The name of the parselet. This is usually something like
     * "com.foo.bar.myParselet" so it can be enabled via 
     * "com.foo.bar.myParselet.enabled=true". The only time this
     * is not required is on abstract classes.
     */
    String name() default "";
    
    /**
     * All the possible classes this parselet can emit. Note, this
     * should be the exact abstract class(es) that are expected so
     * when other parselets ask for a certain type of value, this
     * parselet will be used to try. The only time this is not 
     * required is on abstract classes.
     */
    Class<?>[] emits() default {};
    
    /**
     * All the possible classes emitted from other parselets that this
     * parselet needs.
     */
    Class<?>[] needs() default {};
    
    /**
     * All the parselets this parselet is intended to replace. This is only
     * used if specifically overriding a certain set of parselets.
     */
    Class<? extends Parselet<?>>[] replaces() default {};
    
    /**
     * The precedence of this parselet. This must be a value that can be
     * parsed by {@link java.math.BigDecimal#BigDecimal(String)}. 
     * When a certain value is asked for, the parselet with the lowest
     * number is used.
     */
    String precedence() default "0";
}
