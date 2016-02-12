package com.belk.pep.domain;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the PET_ATTRIBUTE_VALUE database table.
 *
 */
@Entity
@Table(name="PET_ATTRIBUTE_VALUE")
@NamedQuery(name="PetAttributeValue.findAll", query="SELECT p FROM PetAttributeValue p")
public class PetAttributeValue implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The id. */
    @EmbeddedId
    private PetAttributeValuePK id;

    /** The attribute field value. */
    @Column(name="ATTRIBUTE_FIELD_VALUE")
    private String attributeFieldValue;

    /** The created by. */
    @Column(name="CREATED_BY")
    private String createdBy;

    /** The created date. */
    @Column(name="CREATED_DATE")
    private Timestamp createdDate;

    /** The updated by. */
    @Column(name="UPDATED_BY")
    private String updatedBy;

    /** The updated date. */
    @Column(name="UPDATED_DATE")
    private Timestamp updatedDate;

    /** The pet attribute. */
    //bi-directional many-to-one association to PetAttribute
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="ATTRIBUTE_ID", referencedColumnName="ATTRIBUTE_ID" ,insertable = false, updatable = false),
        @JoinColumn(name="CATEGORY_ID", referencedColumnName="CATEGORY_ID")
    })
    private PetAttribute petAttribute;

    public PetAttributeValue() {
    }

    /**
     * Gets the attribute field value.
     *
     * @return the attribute field value
     */
    public String getAttributeFieldValue() {
        return this.attributeFieldValue;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public PetAttributeValuePK getId() {
        return this.id;
    }

    /**
     * Gets the pet attribute.
     *
     * @return the pet attribute
     */
    public PetAttribute getPetAttribute() {
        return this.petAttribute;
    }

    /**
     * Gets the updated by.
     *
     * @return the updated by
     */
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * Gets the updated date.
     *
     * @return the updated date
     */
    public Timestamp getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * Sets the attribute field value.
     *
     * @param attributeFieldValue the new attribute field value
     */
    public void setAttributeFieldValue(String attributeFieldValue) {
        this.attributeFieldValue = attributeFieldValue;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(PetAttributeValuePK id) {
        this.id = id;
    }

    /**
     * Sets the pet attribute.
     *
     * @param petAttribute the new pet attribute
     */
    public void setPetAttribute(PetAttribute petAttribute) {
        this.petAttribute = petAttribute;
    }

    /**
     * Sets the updated by.
     *
     * @param updatedBy the new updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

}