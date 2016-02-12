package com.belk.pep.util;
/**
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.belk.pep.constants.ExternalVendorLoginConstants;
import com.belk.pep.controller.ExternalVendorLoginController;
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
	String propertieFileName=ExternalVendorLoginConstants.VENDOR_LOGIN_PROPERTIES_FILE_NAME;
	InputStream inputStream = ExternalVendorLoginController.class.getClassLoader().getResourceAsStream(propertieFileName);
	prop.load(inputStream);
	return prop;
}
}
