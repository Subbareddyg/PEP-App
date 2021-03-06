/**
 * 
 */
package com.belk.pep.exception.checked;


/**
 * The Class PEPDelegateException.
 */
public class PEPDelegateException extends Exception {

 
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5640577317261269888L;


    /**
     * Instantiates a new PEP delete exception.
     */
    public PEPDelegateException() {
        
    }
    
    
    /**
     * Instantiates a new PEP fetch exception.
     *
     * @param message the message
     */
    public PEPDelegateException(String message)
    {
        super(message);
    }

   
    /**
     * Instantiates a new PEP fetch exception.
     *
     * @param cause the cause
     */
    public PEPDelegateException(Throwable cause)
    {
        super(cause);
    }

  
    /**
     * Instantiates a new PEP fetch exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PEPDelegateException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
