package com.belk.pep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


/**
 * The Class DateUtil.
 */
public class DateUtil {
    
	private DateUtil(){
		
	}
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(DateUtil.class.getName());

    /**
     * String to date.
     *
     * @param sDate the s date
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date stringToDate(final String sDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       // Date Format is  "07/06/2013".
        Date date = new Date();
        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
           LOGGER.error("Error in DateUtil.stringToDate() -->" + e.getMessage());
        }
        return date;
    }
    

    /**
     * String to date.
     *
     * @param sDate the s date
     * @return the date
     * @throws ParseException the parse exception
     * @author AFUPYB3
     */
    public static Date stringToDateMMddyyyy(final String sDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
       // Date format is "07/06/2013".
        Date date = new Date();
        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
        	LOGGER.error("Error in DateUtil.stringToDateMMddyyyy() -->" + e.getMessage());
        }
        return date;
    }



    /**
     * String to String date in MM/dd/yyyy format.
     * @param sDate the s date
     * @return the date
     * @throws ParseException the parse exception
     * @author AFUPYB3
     */
    public static String stringToStringMMddyyyy(final String sDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
       // Date format is "07/06/2013".
        Date date = new Date();
        String dateSt = "";
        try {
        	date = formatter1.parse(sDate);
            dateSt = formatter.format(date);
        } catch (ParseException e) {
        	LOGGER.error("Error in DateUtil.stringToStringMMddyyyy() -->" + e.getMessage());
        }
        return dateSt;
    }
	
    
    /**
     * Date to String date in MM/dd/yyyy format.
     * @param sDate the s date
     * @return the date
     * @throws ParseException the parse exception
     * @author AFUPYB3
     */
    public static String DateToStringMMddyyyy(final Date sDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateSt = "";
        try {
            dateSt = formatter.format(sDate);
        } catch (Exception e) {
        	LOGGER.error("Error in DateUtil.stringToStringMMddyyyy() -->" + e.getMessage());
        }
        return dateSt;
    }

}
