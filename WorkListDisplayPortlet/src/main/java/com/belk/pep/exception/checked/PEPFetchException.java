
package com.belk.pep.exception.checked;

/**
 * The Class PEPFetchException.
 *
 * @author AFUSOS3
 */
public class PEPFetchException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6061771344614932729L;

  
    /**
     * Instantiates a new PEP fetch exception.
     */
    public PEPFetchException() {
       
    }
    
  
   /**
    * Instantiates a new PEP fetch exception.
    *
    * @param message the message
    */
   public PEPFetchException(String message)
   {
       super(message);
   }

  
   /**
    * Instantiates a new PEP fetch exception.
    *
    * @param cause the cause
    */
   public PEPFetchException(Throwable cause)
   {
       super(cause);
   }

 
   /**
    * Instantiates a new PEP fetch exception.
    *
    * @param message the message
    * @param cause the cause
    */
   public PEPFetchException(String message, Throwable cause)
   {
       super(message, cause);
   }


}
