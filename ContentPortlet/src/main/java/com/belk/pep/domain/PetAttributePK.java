package com.belk.pep.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the PET_ATTRIBUTE database table.
 *
 */
@Embeddable
public class PetAttributePK implements Serializable {

    /** The Constant serialVersionUID. */
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    /** The attribute id. */
    @Column(name="ATTRIBUTE_ID")
    private String attributeId;

    /** The category id. */
    @Column(name="CATEGORY_ID")
    private String categoryId;

    /**
     * Instantiates a new pet attribute pk.
     */
    public PetAttributePK() {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PetAttributePK)) {
            return false;
        }
        final PetAttributePK castOther = (PetAttributePK)other;
        return
                this.attributeId.equals(castOther.attributeId)
                && this.categoryId.equals(castOther.categoryId);
    }

    /**
     * Gets the attribute id.
     *
     * @return the attribute id
     */
    public String getAttributeId() {
        return this.attributeId;
    }

    /**
     * Gets the category id.
     *
     * @return the category id
     */
    public String getCategoryId() {
        return this.categoryId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.attributeId.hashCode();
        hash = hash * prime + this.categoryId.hashCode();

        return hash;
    }

    /**
     * Sets the attribute id.
     *
     * @param attributeId the new attribute id
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Sets the category id.
     *
     * @param categoryId the new category id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}