package com.agilistanbul.darklord.commons.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Lemi Orhan Ergin
 * @since 10.12.2013
 */
public class ResourceUtils {

    public static File getClasspathResource(Class clazz, String relativePath) throws IOException {
        try {
            String absolutePath = clazz.getResource(relativePath.startsWith("/") ? relativePath : "/" + relativePath).getPath();
            return new File(absolutePath);
        } catch (Exception e) {
            throw new IOException("Classpath resource cannot be detected", e);
        }
    }

    public static File getFile(Class clazz, String path) throws IOException {
        File absoluteFile = new File(path);
        return absoluteFile.exists() ? absoluteFile : getClasspathResource(clazz, path);
    }
}
