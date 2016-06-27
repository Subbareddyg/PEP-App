package com.belk.pep.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.belk.pep.exception.checked.PEPFetchException;

/** The Class GroupingUtil. */
public class GroupingUtil {
	
	private GroupingUtil(){
		
	}
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(GroupingUtil.class.getName());

	/** Method to get the STring datatype from CLOB.
	 * 
	 * @param data
	 *            Clob
	 * @return sb String
	 * 
	 *         Method added For PIM Phase 2 - Regular Item Copy Attribute Date:
	 *         05/16/2016 Added By: Cognizant
	 * @throws PEPFetchException */
	public static String clobToString(Clob data) throws PEPFetchException {
		LOGGER.info("***Entering clobToString() method.");
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			//read the line.
			int b;
			while (-1 != (b = br.read())) {
				sb.append((char)b);
			}
			// Close the buffereader.
			br.close();
		} catch (SQLException e) {
			LOGGER.error("Exception in clobToString() method. -- " + e.getMessage());
			throw new PEPFetchException(e);
		} catch (IOException e) {
			LOGGER.error("Exception in clobToString() method. -- " + e.getMessage());
			throw new PEPFetchException(e);
		}
		LOGGER.info("***Exiting clobToString() method.");
		return sb.toString();
	}

	/** Method added to Check null values.
	 * 
	 * @param objectValue
	 *            Object
	 * @return String
	 * 
	 *         Method added For PIM Phase 2 Date: 05/18/2016 Added By: Cognizant */
	public static String checkNull(Object objectValue) {
		String valueStr = "";
		// checkNul of the String.
		if (objectValue == null) {
			valueStr = "";
		} else {
			valueStr = objectValue.toString().trim();
		}
		return valueStr;
	}
	
	
	/**
	 * This method is used to get the comma separated value for query in parameter.
	 * @param inputVal
	 * @return
	 */
	public static String getInValForQuery(String inputVal) {
	
	    String inOutputval = "";
	    // get the inputVal.
		inputVal = checkNull(inputVal);
	    String[] valArr = inputVal.split(",");
	    for(int i = 0; i < valArr.length; i++){
	          if(("").equals(inOutputval)){
	                inOutputval = "\'" + valArr[i].trim() + "\'";
	          }else{
				  StringBuilder strBuilder = new StringBuilder();
				  strBuilder.append(inOutputval);
				  strBuilder.append(",\'");
				  strBuilder.append(valArr[i].trim());
				  strBuilder.append("\'");
	              inOutputval = strBuilder.toString();
	          }
	    }
	    return inOutputval;
	}


}
