package com.belk.pep;


import com.belk.pep.app.DeleteImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;
public class App {
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(DeleteImage.class.getName());
    
    public static void main(String[] args) {
        App app = new App();
        app.printThemAll();
    }

    private void printThemAll() {

        Properties prop = new Properties();
        InputStream input = null;
        String filename = "config.properties";
        try {


            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                LOGGER.debug("Sorry, unable to find " + filename);
                return;
            }

            prop.load(input);

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                LOGGER.debug("Key : " + key + ", Value : " + value);
            }

        } catch (IOException ex) {
            LOGGER.error("Exception occurred while trying to read from filename: " 
                    + filename + ", error message: " +ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {

                }
            }
        }
    }
}