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
     * @param updatedBy the updated by
     */
    public UpdateContentStatusDataObject(String petId, String state,String updatedBy, String petStatus) {
        super();
        this.petId = petId;
        this.state = state;
        this.petStatus = petStatus;
        this.updatedBy=updatedBy;
    }
    
    /** The pet id. */
    private String petId;

    /** The state. */
    private String  state;
    
    /** The updated by. */
    private String  updatedBy;
    
    private String petStatus;

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

    /**
     * Gets the updated by.
     *
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the updated by.
     *
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    
    /**
     * Gets the petStatus .
     *
     * @return the petStatus
     */
    public String getPetStatus() {
        return petStatus;
    }

    /**
     * Sets the petStatus.
     *
     * @param petStatus the petStatus to set
     */
    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UpdateContentStatusDataObject [petId=" + petId + ", state="
            + state + ", updatedBy=" + updatedBy + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((petId == null) ? 0 : petId.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result =
            prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UpdateContentStatusDataObject other =
            (UpdateContentStatusDataObject) obj;
        if (petId == null) {
            if (other.petId != null)
                return false;
        }
        else if (!petId.equals(other.petId))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        }
        else if (!state.equals(other.state))
            return false;
        if (updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        }
        else if (!updatedBy.equals(other.updatedBy))
            return false;
        return true;
    }
    
    
    

}
