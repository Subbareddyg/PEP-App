
package com.belk.pep.vo;

import java.io.Serializable;

/**
 * The Class IPHMappingVO.
 *
 * @author AFUSOS3
 */
public class IPHMappingVO  implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The category id. */
    private String categoryId;


    /** The pet id. */
    private String petId;

    /**
     * Instantiates a new IPH mapping vo.
     */
    public IPHMappingVO() {

    }

    /**
     * Gets the category id.
     *
     * @return the categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Gets the pet id.
     *
     * @return the petId
     */
    public String getPetId() {
        return petId;
    }

    /**
     * Sets the category id.
     *
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
        return "IPHMappingVO [categoryId=" + categoryId + ", petId=" + petId
                + "]";
    }




}
