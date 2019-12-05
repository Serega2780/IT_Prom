package org.test.hrdept.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyReader {
    private static final String fileName = "application.properties";
    private static Properties properties;

    private final static Logger logger = Logger.getLogger(PropertyReader.class.getName());

    public static String getProperty(String propertyName) {
        if (properties == null) {
            properties = readProp();
        }
        return properties.getProperty(propertyName);
    }

    private static Properties readProp() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(fileName)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            logger.severe("File issues...");
        }

        return properties;
    }
}
