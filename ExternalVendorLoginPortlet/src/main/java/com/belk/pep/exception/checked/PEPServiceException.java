
package com.belk.pep.exception.checked;

/**
 * The Class PEPServiceException.
 * @author AFUBXJ1
 *
 */
public class PEPServiceException  extends Exception {

   
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5890660599390054981L;

   
    /**
     * Instantiates a new PEP service exception.
     */
    public PEPServiceException() {
        
    }
    
   
    /**
     * Instantiates a new PEP service exception.
     *
     * @param message the message
     */
    public PEPServiceException(String message)
    {
        super(message);
    }

  
    /**
     * Instantiates a new PEP service exception.
     *
     * @param cause the cause
     */
    public PEPServiceException(Throwable cause)
    {
        super(cause);
    }

   
    /**
     * Instantiates a new PEP service exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PEPServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
