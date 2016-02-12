package com.belk.pep.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the PET_ATTRIBUTE_VALUE database table.
 *
 */
@Embeddable
public class PetAttributeValuePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    /** The attribute id. */
    @Column(name="ATTRIBUTE_ID", insertable=false, updatable=false)
    private String attributeId;

    /** The category id. */
    @Column(name="CATEGORY_ID", insertable=false, updatable=false)
    private String categoryId;

    /** The attribute field value seq. */
    @Column(name="ATTRIBUTE_FIELD_VALUE_SEQ")
    private String attributeFieldValueSeq;

    /**
     * Instantiates a new pet attribute value pk.
     */
    public PetAttributeValuePK() {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PetAttributeValuePK)) {
            return false;
        }
        final PetAttributeValuePK castOther = (PetAttributeValuePK)other;
        return
                this.attributeId.equals(castOther.attributeId)
                && this.categoryId.equals(castOther.categoryId)
                && this.attributeFieldValueSeq.equals(castOther.attributeFieldValueSeq);
    }

    /**
     * Gets the attribute field value seq.
     *
     * @return the attribute field value seq
     */
    public String getAttributeFieldValueSeq() {
        return this.attributeFieldValueSeq;
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
        hash = hash * prime + this.attributeFieldValueSeq.hashCode();

        return hash;
    }

    /**
     * Sets the attribute field value seq.
     *
     * @param attributeFieldValueSeq the new attribute field value seq
     */
    public void setAttributeFieldValueSeq(String attributeFieldValueSeq) {
        this.attributeFieldValueSeq = attributeFieldValueSeq;
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