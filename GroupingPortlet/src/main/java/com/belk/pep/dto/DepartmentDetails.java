package com.belk.pep.dto;

/**
 * Class to hold the department details retrieved from database.
 * 
 * Class added For PIM Phase 2 - Group Search 
 * Date: 05/25/2016
 * Added By: Cognizant
 */
public class DepartmentDetails {
    
    /** The id. */
    private String id;
    
    /** The desc. */
    private String desc;
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Gets the desc.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Sets the desc.
     *
     * @param desc the new desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
