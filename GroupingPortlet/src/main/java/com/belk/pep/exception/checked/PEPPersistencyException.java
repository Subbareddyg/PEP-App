package com.belk.pep.exception.checked;

/**
 * The Class PEPPersistencyException.
 */
public class PEPPersistencyException  extends Exception{

  
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8845289404857808663L;

    /**
     * Instantiates a new PEP persistency exception.
     */
    public PEPPersistencyException() {
    	// To be implemented.
    }
    
    /**
     * Instantiates a new PEP persistency exception.
     *
     * @param message the message
     */
    public PEPPersistencyException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new PEP persistency exception.
     *
     * @param cause the cause
     */
    public PEPPersistencyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Instantiates a new PEP persistency exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PEPPersistencyException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
