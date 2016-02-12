/**
 * 
 */
package com.belk.pep.exception.checked;

/**
 * @author AFUHMA1
 *
 */


public class OrinNotFoundException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6061771344614932729L;

  
    /**
     * Instantiates a new PEP fetch exception.
     */
    public OrinNotFoundException() {
       
    }
    
  
   /**
    * Instantiates a new PEP fetch exception.
    *
    * @param message the message
    */
   public OrinNotFoundException(String message)
   {
       super(message);
   }

  
   /**
    * Instantiates a new PEP fetch exception.
    *
    * @param cause the cause
    */
   public OrinNotFoundException(Throwable cause)
   {
       super(cause);
   }

 
   /**
    * Instantiates a new PEP fetch exception.
    *
    * @param message the message
    * @param cause the cause
    */
   public OrinNotFoundException(String message, Throwable cause)
   {
       super(message, cause);
   }


}

