package com.belk.pep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Class to load the properties file.
 * 
 */
public class PropertyLoader {

    /**
     * Instance variable to hold the configuration loader instance.
     */
    private volatile static PropertyLoader instance = null;
    private static final Pattern pattern = Pattern.compile("\\$\\{([^}]*)\\}");

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());

    /**
     * This method returns an instance of ConfigurationLoader.
     * 
     * @return ConfigurationLoader: The instance of configuration loader.
     */
    public static PropertyLoader getInstance() {
        if (instance == null) {
            instance = new PropertyLoader();
        }
        // }
        return instance;
    }

    /**
     * 
     * @param file
     * @return
     */
    public Properties getConnectionProperties(String file) {
        LOGGER.debug("file--->" + file);
        Properties prop = null;
        InputStream inputStream = null;

        prop = new Properties();
        try {
            inputStream = new FileInputStream(new File(file));
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            LOGGER.error(" FileNotFoundException " , e);
        } catch (IOException e) {
            LOGGER.error("Inside IOException " , e);
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("Error while reading the properties file " , e);
            }
        }

        return prop;
    }

    /**
     * This method return properties reading from file.
     * 
     * @param fileName
     * @return
     */
    public static Properties getPropertyLoader(String fileName) {
        Properties properties = new Properties();
        InputStream input = null;
        LOGGER.debug(" Entering getPropertyLoader");
        try {

            // Load the file.
            input = PropertyLoader.class.getClassLoader().getResourceAsStream(fileName);

            properties.load(input);
            // Set the property.
            Enumeration e = properties.propertyNames();
            while (e.hasMoreElements()) {
                // Pull; the key value pair.
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);
                // match the pattern.
                Matcher matchPattern = pattern.matcher(value);
                if (matchPattern.find()) {
                    properties.setProperty(key, System.getenv(matchPattern.group(1)));
                }
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("Inside FileNotFoundException " , e);
        } catch (IOException e) {
            LOGGER.error("Inside IOException " , e);
        } catch (Exception e) {
            LOGGER.error("Inside error " , e);
        }
        LOGGER.debug(" Exiting getPropertyLoader");
        return properties;
    }

}
