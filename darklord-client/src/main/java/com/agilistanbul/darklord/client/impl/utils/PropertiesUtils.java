package com.agilistanbul.darklord.client.impl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Lemi Orhan Ergin
 * @since 10.12.2013
 */
public class PropertiesUtils {

    public static Properties loadFromFilesystemResource(String absolutePath) throws IOException {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(new File(absolutePath)));
            return props;
        } catch (IOException e) {
            throw new IOException("Properties file cannot be loaded from absolute path " + absolutePath, e);
        }
    }

    public static Properties loadFromClasspathResource(Class clazz, String relativePath) throws IOException {
        try {
            String absolutePath = ResourceUtils.getClasspathResource(clazz, relativePath).getAbsolutePath();
            Properties props = new Properties();
            props.load(new FileInputStream(new File(absolutePath)));
            return props;
        } catch (Exception e) {
            throw new IOException("Properties file cannot be loaded from relative path " + relativePath, e);
        }
    }

}
