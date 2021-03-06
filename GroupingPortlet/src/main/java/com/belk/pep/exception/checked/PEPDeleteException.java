/**
 * 
 */
package com.belk.pep.exception.checked;

/**
 * The Class PEPDeleteException.
 *
 * @author AFUSAM1
 */
public class PEPDeleteException extends Exception {

 
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5640577317261269888L;


    /**
     * Instantiates a new PEP delete exception.
     */
    public PEPDeleteException() {
        // To be implemented
    }
    
    
    /**
     * Instantiates a new PEP fetch exception.
     *
     * @param message the message
     */
    public PEPDeleteException(String message)
    {
        super(message);
    }

   
    /**
     * Instantiates a new PEP fetch exception.
     *
     * @param cause the cause
     */
    public PEPDeleteException(Throwable cause)
    {
        super(cause);
    }

  
    /**
     * Instantiates a new PEP fetch exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PEPDeleteException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
