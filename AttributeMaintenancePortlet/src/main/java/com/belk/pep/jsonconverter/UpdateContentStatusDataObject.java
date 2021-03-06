package com.belk.pep.jsonconverter;

/**
 * The Class UpdateContentStatusDataObject.
 */
public class UpdateContentStatusDataObject {
    
    

    /**
     * Instantiates a new update content status data object.
     */
    public UpdateContentStatusDataObject() {
       
    }
  
    /**
     * Instantiates a new update content status data object.
     *
     * @param petId the pet id
     * @param state the state
     */
    public UpdateContentStatusDataObject(String petId, String state) {
        super();
        this.petId = petId;
        this.state = state;
    }
    
    /** The pet id. */
    private String petId;

    /** The state. */
    private String  state;

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

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UpdateContentStatusDataObject [petId=" + petId + ", state="
            + state + "]";
    }
    
    
    

}
