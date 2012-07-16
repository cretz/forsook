package org.forsook.parser.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionUtils {

    public static Set<Class<?>> getAllClassesFromPackage(Package pkg, boolean recurse) {
        return getAllClassesFromPackage(pkg.getName(), recurse);
    }
    
    public static Set<Class<?>> getAllClassesFromPackage(String pkg, boolean recurse) {
        return applyClassesFromPackage(pkg, new HashSet<Class<?>>(), recurse);
    }

    //adapted from: http://stackoverflow.com/questions/1456930/how-do-i-read-all-classes-from-a-java-package-in-the-classpath
    public static Set<Class<?>> applyClassesFromPackage(String pkg, Set<Class<?>> set, boolean recurse) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String packageName = pkg.replace(".", "/");
            URL packageUrl = classLoader.getResource(packageName);
            if (packageUrl.getProtocol().equals("jar")) {
                // build jar file name, then loop through zipped entries
                String jarFileName = URLDecoder.decode(packageUrl.getFile(), "UTF-8");
                jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
                JarFile jf = new JarFile(jarFileName);
                Enumeration<JarEntry> jarEntries = jf.entries();
                while (jarEntries.hasMoreElements()) {
                    JarEntry entry = jarEntries.nextElement();
                    if (entry.getName().startsWith(packageName)) {
                        //class?
                        if (entry.getName().endsWith(".class") && entry.getName().indexOf('.', 
                                packageName.length()) == entry.getName().length() - 5) {
                            set.add(Class.forName(entry.getName().substring(
                                    0, entry.getName().length() - 6).replace('/', '.')));
                        } else if (recurse && entry.isDirectory()) {
                            //recursing?
                            applyClassesFromPackage(entry.getName().replace('/', '.'), set, recurse);
                        }
                    }
                }
            //classpath
            } else {
                File folder = new File(packageUrl.getFile());
                for (File actual : folder.listFiles()) {
                    if (actual.getName().endsWith(".class")) {
                        set.add(Class.forName(pkg + '.' + actual.getName().
                                substring(0, actual.getName().length() - 6)));
                    } else if (recurse && actual.isDirectory()) {
                        applyClassesFromPackage(pkg + '.' + actual.getName(), set, recurse);
                    }
                }
            }
            return set;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private ReflectionUtils() {
    }
}
