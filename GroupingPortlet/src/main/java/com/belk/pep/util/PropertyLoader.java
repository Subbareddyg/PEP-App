/*
 * Created on Dec 7, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.belk.pep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/** Property file */
public class PropertyLoader {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());
	/** Instance variable to hold the configuration loader instance. */
	private volatile static PropertyLoader instance = null;
	private final static Pattern pattern = Pattern.compile("\\$\\{([^}]*)\\}");

	/** This method returns an instance of ConfigurationLoader.
	 * 
	 * @return ConfigurationLoader: The instance of configuration loader. */
	public static PropertyLoader getInstance() {
		if (instance == null) {
			instance = new PropertyLoader();
		}
		// }
		return instance;
	}

	/** @param file
	 * @return */
	public Properties getConnectionProperties(String file) {
		LOGGER.info(" Entering getConnectionProperties");
		Properties prop = null;
		InputStream inputStream = null;
		if (prop == null) {
			prop = new Properties();
			try {
				inputStream = new FileInputStream(new File(file));
				prop.load(inputStream);
			} catch (FileNotFoundException e) {
				LOGGER.error("Inside FileNotFoundException " + e);
			} catch (IOException e) {
				LOGGER.error("Inside IOException" + e);
			}

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("Inside IOException " + e);
				}
			}

		}
		LOGGER.info(" Exiting getConnectionProperties");
		return prop;
	}

	public static Properties getPropertyLoader(String fileName) {
		Properties properties = new Properties();
		InputStream input = null;
		LOGGER.info(" Entering getPropertyLoader");
		try {


			input = PropertyLoader.class.getClassLoader().getResourceAsStream(fileName);
			
			properties.load(input);

			Enumeration e = properties.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = properties.getProperty(key);
				Matcher matchPattern = pattern.matcher(value);
				if (matchPattern.find()) {
					properties.setProperty(key, System.getenv(matchPattern.group(1)));
				}
			}
			LOGGER.info("properties" + properties);

		} catch (FileNotFoundException e) {
			LOGGER.error("Inside FileNotFoundException " + e);
		} catch (IOException e) {
			LOGGER.error("Inside IOException " + e);
		}  catch (Exception e) {
			LOGGER.error("Inside error " + e);
		}
		LOGGER.info(" Exiting getPropertyLoader");
		return properties;
	}

}
