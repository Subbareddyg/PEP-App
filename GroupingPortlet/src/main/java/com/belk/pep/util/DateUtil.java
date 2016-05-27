package com.belk.pep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DateUtil.
 */
public class DateUtil {
    
    /**
     * String to date.
     *
     * @param sDate the s date
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date stringToDate(String sDate) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       // String dateInString = "07/06/2013";
        Date date = new Date();
        try {
            date = formatter.parse(sDate);
           // System.out.println(date);
          //  System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
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
    public static Date stringToDateMMddyyyy(String sDate) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
       // String dateInString = "07/06/2013";
        Date date = new Date();
        try {
            date = formatter.parse(sDate);
           // System.out.println(date);
          //  System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
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
    public static String stringToStringMMddyyyy(String sDate) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
       // String dateInString = "07/06/2013";
        Date date = new Date();
        String dateSt = "";
        try {
        	date = formatter1.parse(sDate);
            dateSt = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateSt;
    }
	
    

}
