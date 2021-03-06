
package com.belk.pep.util;

/**
 * The Class ExtractColorCode.
 *
 * @author AFUSOS3
 */
public class ExtractColorCode {

    /**
     * Instantiates a new extract color code.
     */
    public ExtractColorCode() {
        
    }

    
    /**
     * Gets the last three digit nrf color code.
     *
     * @param styleColorOrinNumber the style color orin number
     * @return the last three digit nrf color code
     */
    public static String getLastThreeDigitNRFColorCode(String styleColorOrinNumber) {
        if(styleColorOrinNumber.length() > 3)
        {
            return styleColorOrinNumber.substring(styleColorOrinNumber.length()-3);
        }
        else
        {
            return styleColorOrinNumber;
        }
    }
    
    
}
