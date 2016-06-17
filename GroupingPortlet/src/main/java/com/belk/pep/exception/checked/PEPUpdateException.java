
package com.belk.pep.exception.checked;

/**
 * The Class PEPUpdateException.
 *
 * @author AFUSAM1
 */
public class PEPUpdateException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4067136446179197038L;

    
    /**
     * Instantiates a new PEP update exception.
     */
    public PEPUpdateException() {
    	// To be implemented.
    }
    
    
    /**
     * Instantiates a new PEP update exception.
     *
     * @param message the message
     */
    public PEPUpdateException(String message)
    {
        super(message);
    }

 
    /**
     * Instantiates a new PEP update exception.
     *
     * @param cause the cause
     */
    public PEPUpdateException(Throwable cause)
    {
        super(cause);
    }

   
    /**
     * Instantiates a new PEP update exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PEPUpdateException(String message, Throwable cause)
    {
        super(message, cause);
    }


}
