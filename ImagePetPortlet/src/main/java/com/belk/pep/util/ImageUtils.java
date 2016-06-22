/**
 * 
 */
package com.belk.pep.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.belk.pep.exception.checked.PEPFetchException;

/**
 * @author afusyg6
 *
 */
public class ImageUtils {
	private final static Logger LOGGER = Logger.getLogger(ImageUtils.class.getName());
	/** Method to get the STring datatype from CLOB.
	 * 
	 * @param data
	 *            Clob
	 * @return sb String
	 * 
	 
	 * @throws PEPFetchException */
	public static String clobToString(Clob data) throws PEPFetchException {
		LOGGER.info("***Entering clobToString() method.");
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);

			String line;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
			br.close();
		} catch (SQLException e) {
			LOGGER.error("Exception in clobToString() method. -- " + e.getMessage());
			throw new PEPFetchException(e);
		} catch (IOException e) {
			LOGGER.error("Exception in clobToString() method. -- " + e.getMessage());
			throw new PEPFetchException(e);
		}
		LOGGER.info("***Exiting clobToString() method."+sb.toString());
		return sb.toString();
	}

}
