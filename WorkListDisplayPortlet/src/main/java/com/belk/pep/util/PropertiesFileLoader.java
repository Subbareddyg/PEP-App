package com.belk.pep.util;
/**
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.belk.pep.constants.WorkListDisplayConstants;
import com.belk.pep.controller.WorkListDisplayController;
/**
 * This class is a utility class to load the properties in to the system path.
 * @author AFUBXJ1
 *
 */
public class PropertiesFileLoader {
	/**
	 * This method is responsible for returning the Properties.
	 * @return
	 * @throws IOException
	 */
public static Properties getExternalLoginProperties() throws IOException{
	Properties prop = new Properties();
	String propertieFileName=WorkListDisplayConstants.WORK_LIST_DISPLAY_PROPERTIES_FILE_NAME;
	InputStream inputStream = WorkListDisplayController.class.getClassLoader().getResourceAsStream(propertieFileName);
	prop.load(inputStream);
	return prop;
}

    public static Properties getPropertyLoader(
        String messProp) throws IOException {
        
        Properties prop = new Properties();
        String propertieFileName=messProp;
        InputStream inputStream = WorkListDisplayController.class.getClassLoader().getResourceAsStream(propertieFileName);
        prop.load(inputStream);
        return prop;
    }
}
