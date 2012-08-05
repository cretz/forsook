/*
 * StringSearch.java
 *
 * Created on 14.06.2003.
 *
 * eaio: StringSearch - high-performance pattern matching algorithms in Java
 * Copyright (c) 2003-2010 Johann Burkard (<http://johannburkard.de>)
 * <http://eaio.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.eaio.stringsearch;

import java.lang.reflect.Field;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * The base class for pattern matching algorithm implementations.
 * Most implementations do not maintain state and are thread safe -- one instance
 * can be used by as many threads as required.
 * <p>
 * Most pattern matching algorithms pre-process the pattern in
 * some way. Subclasses of StringSearch allow retrieving the pre-processed
 * pattern to save time if the pattern is used several times.
 * <p>
 * Some of the Objects returned from the pre-processing methods
 * {@link #processBytes(byte[])}, {@link #processChars(char[])} and
 * {@link #processString(String)} might implement
 * the {@link java.io.Serializable} interface and make it possible to
 * serialize pre-processed Objects to disk.
 * <p>
 * When this Class is loaded, an attempt is made to obtain {@link java.lang.reflect.Field} instances of
 * the "offset" and the "value" fields of the {@link java.lang.String} class.
 * These fields are set as accessible. If this succeeds, StringSearch will use
 * Reflection to access the underlying <code>char</code> array in Strings. If
 * not, the <code>char</code> array will be cloned by calling
 * {@link java.lang.String#toCharArray()}.</p>
 *
 * @see <a href="http://johannburkard.de/software/stringsearch/" target="_top">
 * StringSearch &#8211; high-performance pattern matching algorithms in Java</a>
 * @author <a href="http://johannburkard.de">Johann Burkard</a>
 * @version $Id: StringSearch.java 3106 2010-06-29 11:23:00Z johann $
 */
public abstract class StringSearch {

    private static final int CROSSOVER_MACOSX = 50;

    /**
     * The crossover point at which the Reflection based char accessor should
     * be used. The crossover point is set in the static initializer. If a
     * String is longer than this value and Reflection is allowed, it's
     * <code>char</code> array will be accessed through Reflection.
     */
    private static int crossover = 0;

    /**
     * The StringAccess instance.
     */
    static StringAccess activeStringAccess;

    /**
     * The StringAccess class implements the strategy to convert Strings to
     * <code>char</code> arrays and calls the appropriate
     * <code>searchChars</code> method in the given StringSearch instance.
     */
    static class StringAccess {

        /**
         * Instances are created in StringSearch only.
         */
        private StringAccess() {
            super();
        }

        /**
         * Searches a pattern inside a text, using the pre-processed Object and
         * using the given StringSearch instance.
         */
        int searchString(String text, int textStart, int textEnd,
                String pattern, Object processed, StringSearch instance) {

            return instance.searchChars(text.toCharArray(), textStart, textEnd,
                    pattern.toCharArray(), processed);

        }

        /**
         * Searches a pattern inside a text, using the given StringSearch
         * instance.
         */
        int searchString(String text, int textStart, int textEnd,
                String pattern, StringSearch instance) {

            return instance.searchChars(text.toCharArray(), textStart, textEnd,
                    pattern.toCharArray());

        }

        /**
         * Searches a pattern inside a text with at most k mismatches, using
         * the given MismatchSearch instance.
         */
        int[] searchString(String text, int textStart, int textEnd,
                String pattern, int k, MismatchSearch instance) {

            return instance.searchChars(text.toCharArray(), textStart, textEnd,
                    pattern.toCharArray(), k);

        }

        /**
         * Searches a pattern inside a text, using the pre-processed Object and
         * at most k mismatches, using the given MismatchSearch instance.
         */
        int[] searchString(String text, int textStart, int textEnd,
                String pattern, Object processed, int k, MismatchSearch instance) {

            return instance.searchChars(text.toCharArray(), textStart, textEnd,
                    pattern.toCharArray(), processed, k);

        }

        /**
         * Returns the underlying <code>char</code> array.
         *
         * @param s the String, may not be <code>null</code>
         * @return char[]
         */
        char[] getChars(String s) {
            return s.toCharArray();
        }

    }

    /**
     * The ReflectionStringAccess class is used if Reflection can be used to access the
     * underlying <code>char</code> array in Strings to avoid the cloning
     * overhead.
     */
    static class ReflectionStringAccess extends StringAccess {

        private Field value, offset;

        /**
         * Instances are created in StringSearch only.
         *
         * @param value the "value" field in String
         * @param offset the "offset" field in String
         */
        private ReflectionStringAccess(Field value, Field offset) {
            this.value = value;
            this.offset = offset;
        }

        /**
         * @see com.eaio.stringsearch.StringSearch.StringAccess#searchString(
         * String, int, int, String, Object, StringSearch)
         */
        @Override
        int searchString(String text, int textStart, int textEnd,
                String pattern, Object processed, StringSearch instance) {

            int l = text.length();
            if (l > crossover) {
                try {
                    int o = offset.getInt(text);
                    char[] t = (char[]) value.get(text);
                    return instance.searchChars(t, textStart + o, textEnd + o,
                            getChars(pattern), processed)
                            - o;

                }
                catch (IllegalAccessException ex) {
                    synchronized (activeStringAccess) {
                        activeStringAccess = new StringAccess();
                    }
                }
            }

            return super.searchString(text, textStart, textEnd, pattern,
                    processed, instance);

        }

        /**
         * @see com.eaio.stringsearch.StringSearch.StringAccess#searchString(
         * String, int, int, String, StringSearch)
         */
        @Override
        int searchString(String text, int textStart, int textEnd,
                String pattern, StringSearch instance) {

            int l = text.length();
            if (l > crossover) {
                try {
                    int o = offset.getInt(text);
                    char[] t = (char[]) value.get(text);
                    return instance.searchChars(t, textStart + o, textEnd + o,
                            getChars(pattern))
                            - o;
                }
                catch (IllegalAccessException ex) {
                    synchronized (activeStringAccess) {
                        activeStringAccess = new StringAccess();
                    }
                }
            }

            return super.searchString(text, textStart, textEnd, pattern,
                    instance);

        }

        /**
         * @see com.eaio.stringsearch.StringSearch.StringAccess#searchString(
         * String, int, int, String, int, MismatchSearch)
         */
        @Override
        int[] searchString(String text, int textStart, int textEnd,
                String pattern, int k, MismatchSearch instance) {

            int l = text.length();
            if (l > crossover) {
                try {
                    int o = offset.getInt(text);
                    char[] t = (char[]) value.get(text);
                    int[] r = instance.searchChars(t, textStart + o, textEnd
                            + o, getChars(pattern), k);
                    if (r[0] != -1) {
                        r[0] -= o;
                    }
                    return r;
                }
                catch (IllegalAccessException ex) {
                    synchronized (activeStringAccess) {
                        activeStringAccess = new StringAccess();
                    }
                }
            }

            return super.searchString(text, textStart, textEnd, pattern, k,
                    instance);

        }

        /**
         * @see com.eaio.stringsearch.StringSearch.StringAccess#searchString(
         * String, int, int, String, Object, int, MismatchSearch)
         */
        @Override
        int[] searchString(String text, int textStart, int textEnd,
                String pattern, Object processed, int k, MismatchSearch instance) {

            int l = text.length();
            if (l > crossover) {
                try {
                    int o = offset.getInt(text);
                    char[] t = (char[]) value.get(text);
                    int[] r = instance.searchChars(t, textStart + o, textEnd
                            + o, getChars(pattern), processed, k);
                    if (r[0] != -1) {
                        r[0] -= o;
                    }
                    return r;
                }
                catch (IllegalAccessException ex) {
                    synchronized (activeStringAccess) {
                        activeStringAccess = new StringAccess();
                    }
                }
            }

            return super.searchString(text, textStart, textEnd, pattern,
                    processed, k, instance);

        }

        /**
         * Tries to return the underlying <code>char</code> array directly.
         * Only works if the "offset" field is 0 and the "count" field is equal
         * to the String's length.
         * 
         * @see com.eaio.stringsearch.StringSearch.StringAccess#getChars(
         * java.lang.String)
         */
        @Override
        char[] getChars(String s) {
            int l = s.length();
            if (l > crossover) {
                try {
                    if (offset.getInt(s) != 0) {
                        return super.getChars(s);
                    }
                    char[] c = (char[]) value.get(s);
                    if (c.length != l) {
                        return super.getChars(s);
                    }
                    return c;
                }
                catch (IllegalAccessException ex) {
                    synchronized (activeStringAccess) {
                        activeStringAccess = new StringAccess();
                    }
                }
            }
            return super.getChars(s);
        }

    }

    static {

        final String shortString = ".";
        shortString.hashCode(); // make sure the cached hashCode is not 0

        Field value = null;
        Field offset = null;

        try {
            Field[] valueOffset = AccessController.doPrivileged(new PrivilegedExceptionAction<Field[]>() {

                public Field[] run() throws Exception {

                    Field[] stringFields = shortString.getClass().getDeclaredFields();

                    Class<? extends char[]> charArray = new char[0].getClass();

                    Field val = null, off = null;

                    for (int i = 0; i < stringFields.length; ++i) {
                        if (stringFields[i].getType() == charArray) {
                            val = stringFields[i];
                            val.setAccessible(true);
                        }
                        else if (stringFields[i].getType() == Integer.TYPE) {
                            stringFields[i].setAccessible(true);

                            if (stringFields[i].getInt(shortString) == 0) {
                                off = stringFields[i];
                            }

                        }

                    }

                    return new Field[] { val, off };
                }

            });

            value = valueOffset[0];
            offset = valueOffset[1];

        }
        catch (AccessControlException ex) {
            // Ignored.
        }
        catch (PrivilegedActionException ex) {
            // Ignored.
        }

        if (value != null && offset != null) {

            StringSearch.activeStringAccess = new ReflectionStringAccess(value,
                    offset);

            try {

                String vendor = System.getProperty("java.vendor", "");

                char v = vendor.charAt(0);

                if (v == 'I' || v == 'S') {
                    // Both IBM and Sun are faster when using Reflection. 
                    crossover = 0;
                }

                if (System.getProperty("mrj.version") != null) {
                    crossover = CROSSOVER_MACOSX;
                }
            }
            catch (SecurityException ex) {
                // Ignored.
            }

        }
        else {
            StringSearch.activeStringAccess = new StringAccess();
        }
    }

    /**
     * Returns if Reflection is used to access the underlying <code>char</code>
     * array in Strings.
     * 
     * @return boolean
     */
    public static boolean usesReflection() {
        return activeStringAccess instanceof ReflectionStringAccess;
    }

    /**
     * Attempts to return the underlying <code>char</code> array of a String
     * directly. If Reflection cannot be used, the array is cloned by a call to
     * {@link String#toCharArray()}.
     * 
     * @param s the String
     * @return a <code>char</code> array
     */
    public static char[] getChars(String s) {
        return activeStringAccess.getChars(s);
    }

    /**
     * Constructor for StringSearch. Note that it is not required to create
     * multiple instances of an algorithm. This constructor does nothing.
     */
    protected StringSearch() {
        super();
    }

    /*
     * Pre-processing methods
     */

    /**
     * Pre-processes a <code>byte</code> array. This method should be used if a
     * pattern is searched for more than one time.
     * 
     * @param pattern the <code>byte</code> array containing the pattern, may not
     * be <code>null</code>
     * @return an Object
     */
    public abstract Object processBytes(byte[] pattern);

    /**
     * Pre-processes a <code>char</code> array. This method should be used if a
     * pattern is searched for more than one time.
     * 
     * @param pattern a <code>char</code> array containing the pattern, may not be
     * <code>null</code>
     * @return an Object
     */
    public abstract Object processChars(char[] pattern);

    /**
     * Pre-processes a String. This method should be used if a pattern is searched
     * for more than one time.
     * 
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @return an Object
     * @see #processChars(char[])
     */
    public Object processString(String pattern) {
        return processChars(getChars(pattern));
    }

    /* Byte searching methods */

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the <code>byte</code> array containing the text, may not be
     * <code>null</code>
     * @param pattern the <code>byte</code> array containing the pattern, may not
     * be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchBytes(byte[], int, int, byte[], Object)
     */
    public final int searchBytes(byte[] text, byte[] pattern) {
        return searchBytes(text, 0, text.length, pattern, processBytes(pattern));
    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the <code>byte</code> array containing the text, may not be
     * <code>null</code>
     * @param pattern the pattern to search for, may not be <code>null</code>
     * @param processed an Object as returned from {@link #processBytes(byte[])},
     * may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchBytes(byte[], int, int, byte[], Object)
     */
    public final int searchBytes(byte[] text, byte[] pattern, Object processed) {
        return searchBytes(text, 0, text.length, pattern, processed);
    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the <code>byte</code> array containing the text, may not be
     * <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param pattern the <code>byte</code> array containing the pattern, may not
     * be <code>null</code>
     * @return int the position in the text or -1 if the pattern was not found
     * @see #searchBytes(byte[], int, int, byte[], Object)
     */
    public final int searchBytes(byte[] text, int textStart, byte[] pattern) {
        return searchBytes(text, textStart, text.length, pattern,
                processBytes(pattern));
    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the <code>byte</code> array containing the text, may not be
     * <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param pattern the pattern to search for, may not be <code>null</code>
     * @param processed
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchBytes(byte[], int, int, byte[], Object)
     */
    public final int searchBytes(byte[] text, int textStart, byte[] pattern,
            Object processed) {

        return searchBytes(text, textStart, text.length, pattern, processed);

    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text text the <code>byte</code> array containing the text, may not be
     * <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param textEnd at which position in the text comparing should stop
     * @param pattern the <code>byte</code> array containing the pattern, may not
     * be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchBytes(byte[], int, int, byte[], Object)
     */
    public final int searchBytes(byte[] text, int textStart, int textEnd,
            byte[] pattern) {

        return searchBytes(text, textStart, textEnd, pattern,
                processBytes(pattern));

    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text text the <code>byte</code> array containing the text, may not be
     * <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param textEnd at which position in the text comparing should stop
     * @param pattern the pattern to search for, may not be <code>null</code>
     * @param processed an Object as returned from {@link #processBytes(byte[])},
     * may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #processBytes(byte[])
     */
    public abstract int searchBytes(byte[] text, int textStart, int textEnd,
            byte[] pattern, Object processed);

    /* Char searching methods */

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the character array containing the text, may not be
     * <code>null</code>
     * @param pattern the <code>char</code> array containing the pattern, may not
     * be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchChars(char[] text, char[] pattern) {
        return searchChars(text, 0, text.length, pattern, processChars(pattern));
    }

    /**
     * Returns the index of the pattern in the text using the pre-processed Object.
     * Returns -1 if the pattern was not found.
     * 
     * @param text the character array containing the text, may not be
     * <code>null</code>
     * @param pattern the <code>char</code> array containing the pattern, may not
     * be <code>null</code>
     * @param processed an Object as returned from {@link #processChars(char[])} or
     * {@link #processString(String)}, may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchChars(char[] text, char[] pattern, Object processed) {
        return searchChars(text, 0, text.length, pattern, processed);
    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the character array containing the text, may not be
     * <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param pattern the <code>char</code> array containing the pattern, may not
     * be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchChars(char[] text, int textStart, char[] pattern) {
        return searchChars(text, textStart, text.length, pattern,
                processChars(pattern));
    }

    /**
     * Returns the index of the pattern in the text using the pre-processed Object.
     * Returns -1 if the pattern was not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param pattern the <code>char</code> array containing the pattern, may not
     * be <code>null</code>
     * @param processed an Object as returned from {@link #processChars(char[])} or
     * {@link #processString(String)}, may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchChars(char[] text, int textStart, char[] pattern,
            Object processed) {

        return searchChars(text, textStart, text.length, pattern, processed);

    }

    /**
     * Returns the position in the text at which the pattern was found. Returns -1
     * if the pattern was not found.
     * 
     * @param text the character array containing the text, may not be
     * <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param textEnd at which position in the text comparing should stop
     * @param pattern the <code>char</code> array containing the pattern, may not
     * be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchChars(char[] text, int textStart, int textEnd,
            char[] pattern) {

        return searchChars(text, textStart, textEnd, pattern,
                processChars(pattern));

    }

    /**
     * Returns the index of the pattern in the text using the pre-processed Object.
     * Returns -1 if the pattern was not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param textEnd at which position in the text comparing should stop
     * @param pattern the pattern to search for, may not be <code>null</code>
     * @param processed an Object as returned from {@link #processChars(char[])} or
     * {@link #processString(String)}, may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     */
    public abstract int searchChars(char[] text, int textStart, int textEnd,
            char[] pattern, Object processed);

    /* String searching methods */

    /**
     * Convenience method to search for patterns in Strings. Returns the position
     * in the text at which the pattern was found. Returns -1 if the pattern was
     * not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchString(String text, String pattern) {
        return searchString(text, 0, text.length(), pattern);
    }

    /**
     * Convenience method to search for patterns in Strings. Returns the position
     * in the text at which the pattern was found. Returns -1 if the pattern was
     * not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @param processed an Object as returned from {@link #processChars(char[])} or
     * {@link #processString(String)}, may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchString(String text, String pattern, Object processed) {
        return searchString(text, 0, text.length(), pattern, processed);
    }

    /**
     * Convenience method to search for patterns in Strings. Returns the position
     * in the text at which the pattern was found. Returns -1 if the pattern was
     * not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchString(String text, int textStart, String pattern) {
        return searchString(text, textStart, text.length(), pattern);
    }

    /**
     * Convenience method to search for patterns in Strings. Returns the position
     * in the text at which the pattern was found. Returns -1 if the pattern was
     * not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @param processed an Object as returned from {@link #processChars(char[])} or
     * {@link #processString(String)}, may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[], Object)
     */
    public final int searchString(String text, int textStart, String pattern,
            Object processed) {

        return searchString(text, textStart, text.length(), pattern, processed);

    }

    /**
     * Convenience method to search for patterns in Strings. Returns the position
     * in the text at which the pattern was found. Returns -1 if the pattern was
     * not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param textEnd at which position in the text comparing should stop
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[])
     */
    public final int searchString(String text, int textStart, int textEnd,
            String pattern) {

        return StringSearch.activeStringAccess.searchString(text, textStart,
                textEnd, pattern, this);

    }

    /**
     * Convenience method to search for patterns in Strings. Returns the position
     * in the text at which the pattern was found. Returns -1 if the pattern was
     * not found.
     * 
     * @param text the String containing the text, may not be <code>null</code>
     * @param textStart at which position in the text the comparing should start
     * @param textEnd at which position in the text comparing should stop
     * @param pattern the String containing the pattern, may not be
     * <code>null</code>
     * @param processed an Object as returned from {@link #processChars(char[])} or
     * {@link #processString(String)}, may not be <code>null</code>
     * @return the position in the text or -1 if the pattern was not found
     * @see #searchChars(char[], int, int, char[])
     */
    public final int searchString(String text, int textStart, int textEnd,
            String pattern, Object processed) {

        return StringSearch.activeStringAccess.searchString(text, textStart,
                textEnd, pattern, processed, this);

    }

    /**
     * Returns if the Object's class matches this Object's class.
     * 
     * @param obj the other Object, may be <code>null</code>
     * @return if the Object is equal to this Object
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass().getName().equals(obj.getClass().getName());
    }

    /**
     * Returns the hashCode of the current class' name because all instances of
     * this class are equal.
     * 
     * @return <code>int</code>
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getClass().getName().hashCode();
    }

    /**
     * Returns a String representation of this Object. Simply returns the name of
     * the Class.
     * 
     * @return a String, never <code>null</code>
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getName();
    }

    /**
     * Returns a {@link CharIntMap} of the extent of the given pattern, using
     * the specified default value.
     * 
     * @param pattern the pattern, may not be <code>null</code>
     * @param defaultValue the default value
     * @return a CharIntMap, never <code>null</code>
     * @see CharIntMap#CharIntMap(int, char, int)
     */
    protected final CharIntMap createCharIntMap(char[] pattern, int defaultValue) {
        return createCharIntMap(pattern, pattern.length, defaultValue);
    }

    /**
     * Returns a {@link CharIntMap} of the extent of the given pattern, using
     * the specified default value.
     * 
     * @param pattern the pattern, may not be <code>null</code>
     * @param patternEnd where to stop searching for extent values in the
     * pattern
     * @param defaultValue the default value
     * @return a CharIntMap, never <code>null</code>
     * @see CharIntMap#CharIntMap(int, char, int)
     */
    protected final CharIntMap createCharIntMap(char[] pattern, int patternEnd,
            int defaultValue) {
        char min = Character.MAX_VALUE;
        char max = Character.MIN_VALUE;
        for (int i = 0; i < patternEnd; i++) {
            max = max > pattern[i] ? max : pattern[i];
            min = min < pattern[i] ? min : pattern[i];
        }
        return new CharIntMap(max - min + 1, min, defaultValue);
    }

    /**
     * Interprets the given <code>byte</code> as an <code>unsigned byte</code>.
     * 
     * @param idx the <code>byte</code>
     * @return <code>int</code>
     */
    protected final int index(byte idx) {
        /* Much faster in IBM, see com.eaio.stringsearch.performanceTest.Index. */
        /* And MUCH faster in Sun, too. */
        return idx & 0x000000ff;
    }

}
