package org.forsook.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class IoUtils {

    public static String fileToString(File file) {
        try {
            return streamToString(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Closes stream automatically
     * 
     * @param stream
     * @return
     */
    public static String streamToString(InputStream stream) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(stream, "UTF8");
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (Exception e) { }
        }
    }
    
    private IoUtils() {
    }
}
