/**
 * 
 */
package com.belk.pep.model;

import java.io.Serializable;

/**
 * The Class WebserviceResponse.
 *
 * @author AFUSOS3
 */
public class WebserviceResponse  implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new webservice response.
     */
    public WebserviceResponse() {
       
    }
    
    
    /** The message. */
    private String message;

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    /**
     * Sets the message.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
