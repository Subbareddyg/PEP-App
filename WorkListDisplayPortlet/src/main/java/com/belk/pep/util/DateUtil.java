package com.belk.pep.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

}
