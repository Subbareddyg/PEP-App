package com.belk.pep.util;

/**
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.belk.pep.constants.GroupingConstants;
import com.belk.pep.controller.GroupingController;

/** This class is a utility class to load the properties in to the system path.
 * 
 * @author */
public class PropertiesFileLoader {
	/** This method is responsible for returning the Properties.
	 * 
	 * @return
	 * @throws IOException */
	public static Properties getExternalLoginProperties() throws IOException {
		Properties prop = new Properties();
		String propertieFileName = GroupingConstants.GROUPING_PROPERTIES_FILE_NAME;
		InputStream inputStream = GroupingController.class.getClassLoader().getResourceAsStream(propertieFileName);
		prop.load(inputStream);
		return prop;
	}

	public static Properties getPropertyLoader(String messProp) throws IOException {

		Properties prop = new Properties();
		String propertieFileName = messProp;
		InputStream inputStream = GroupingController.class.getClassLoader().getResourceAsStream(propertieFileName);
		prop.load(inputStream);
		return prop;
	}
}
