/**
 * 
 */
package com.belk.pep.jsonconverter;

/**
 * The Class DataObject.
 *
 * @author AFUSOS3
 */
public class DataObject {

    /**
     * Instantiates a new data object.
     */
    public DataObject() {
       
    }

    

    /**
     * Instantiates a new data object.
     *
     * @param petId the pet id
     * @param categoryId the category id
     */
    public DataObject(String petId, String categoryId) {
        super();
        this.petId = petId;
        this.categoryId = categoryId;
    }



    /** The pet id. */
    private String petId;
    
    /** The category id. */
    private String  categoryId;
    
    /**
     * Gets the pet id.
     *
     * @return the petId
     */
    public String getPetId() {
        return petId;
    }
    
    /**
     * Sets the pet id.
     *
     * @param petId the petId to set
     */
    public void setPetId(String petId) {
        this.petId = petId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DataObject [petId=" + petId + ", categoryId=" + categoryId
            + "]";
    }
    
    
}
