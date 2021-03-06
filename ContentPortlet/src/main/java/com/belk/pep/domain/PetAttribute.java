package com.belk.pep.domain;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the PET_ATTRIBUTE database table.
 *
 */
@Entity
@Table(name="PET_ATTRIBUTE")
@NamedQuery(name="PetAttribute.findAll", query="select {p.*}, {pav.*} from PetAttribute p  join PetAttributeValue pav ON p.id.categoryId=pav.id.categoryId")
public class PetAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PetAttributePK id;

    @Column(name="ATTRIBUTE_FIELD_TYPE")
    private String attributeFieldType;

    @Column(name="ATTRIBUTE_NAME")
    private String attributeName;

    @Column(name="ATTRIBUTE_PATH")
    private String attributePath;

    @Column(name="ATTRIBUTE_STATUS")
    private String attributeStatus;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="CREATED_DATE")
    private Timestamp createdDate;

    @Column(name="DISPLAY_NAME")
    private String displayName;

    @Column(name="HTML_DISPLAY_DESC")
    private String htmlDisplayDesc;

    @Column(name="IS_DISPLAYABLE")
    private String isDisplayable;

    @Column(name="IS_EDITABLE")
    private String isEditable;

    @Column(name="IS_MANDATORY")
    private String isMandatory;

    @Column(name="MAPPING_KEY")
    private String mappingKey;

    @Column(name="MAX_OCCURANCE")
    private String maxOccurance;

    @Column(name="UPDATED_BY")
    private String updatedBy;

    @Column(name="UPDATED_DATE")
    private Timestamp updatedDate;

    //bi-directional many-to-one association to PetAttributeValue
    @OneToMany(mappedBy="petAttribute")
    private List<PetAttributeValue> petAttributeValues;

    /**
     * Instantiates a new pet attribute.
     */
    public PetAttribute() {
    }

    /**
     * Adds the pet attribute value.
     *
     * @param petAttributeValue the pet attribute value
     * @return the pet attribute value
     */
    public PetAttributeValue addPetAttributeValue(PetAttributeValue petAttributeValue) {
        getPetAttributeValues().add(petAttributeValue);
        petAttributeValue.setPetAttribute(this);

        return petAttributeValue;
    }

    /**
     * Gets the attribute field type.
     *
     * @return the attribute field type
     */
    public String getAttributeFieldType() {
        return this.attributeFieldType;
    }

    /**
     * Gets the attribute name.
     *
     * @return the attribute name
     */
    public String getAttributeName() {
        return this.attributeName;
    }

    /**
     * Gets the attribute path.
     *
     * @return the attribute path
     */
    public String getAttributePath() {
        return this.attributePath;
    }

    /**
     * Gets the attribute status.
     *
     * @return the attribute status
     */
    public String getAttributeStatus() {
        return this.attributeStatus;
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
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Gets the html display desc.
     *
     * @return the html display desc
     */
    public String getHtmlDisplayDesc() {
        return this.htmlDisplayDesc;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public PetAttributePK getId() {
        return this.id;
    }

    /**
     * Gets the checks if is displayable.
     *
     * @return the checks if is displayable
     */
    public String getIsDisplayable() {
        return this.isDisplayable;
    }

    /**
     * Gets the checks if is editable.
     *
     * @return the checks if is editable
     */
    public String getIsEditable() {
        return this.isEditable;
    }

    /**
     * Gets the checks if is mandatory.
     *
     * @return the checks if is mandatory
     */
    public String getIsMandatory() {
        return this.isMandatory;
    }

    /**
     * Gets the mapping key.
     *
     * @return the mapping key
     */
    public String getMappingKey() {
        return this.mappingKey;
    }

    /**
     * Gets the max occurance.
     *
     * @return the max occurance
     */
    public String getMaxOccurance() {
        return this.maxOccurance;
    }

    /**
     * Gets the pet attribute values.
     *
     * @return the pet attribute values
     */
    public List<PetAttributeValue> getPetAttributeValues() {
        return this.petAttributeValues;
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
     * Removes the pet attribute value.
     *
     * @param petAttributeValue the pet attribute value
     * @return the pet attribute value
     */
    public PetAttributeValue removePetAttributeValue(PetAttributeValue petAttributeValue) {
        getPetAttributeValues().remove(petAttributeValue);
        petAttributeValue.setPetAttribute(null);

        return petAttributeValue;
    }

    /**
     * Sets the attribute field type.
     *
     * @param attributeFieldType the new attribute field type
     */
    public void setAttributeFieldType(String attributeFieldType) {
        this.attributeFieldType = attributeFieldType;
    }

    /**
     * Sets the attribute name.
     *
     * @param attributeName the new attribute name
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Sets the attribute path.
     *
     * @param attributePath the new attribute path
     */
    public void setAttributePath(String attributePath) {
        this.attributePath = attributePath;
    }

    /**
     * Sets the attribute status.
     *
     * @param attributeStatus the new attribute status
     */
    public void setAttributeStatus(String attributeStatus) {
        this.attributeStatus = attributeStatus;
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
     * Sets the display name.
     *
     * @param displayName the new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the html display desc.
     *
     * @param htmlDisplayDesc the new html display desc
     */
    public void setHtmlDisplayDesc(String htmlDisplayDesc) {
        this.htmlDisplayDesc = htmlDisplayDesc;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(PetAttributePK id) {
        this.id = id;
    }

    /**
     * Sets the checks if is displayable.
     *
     * @param isDisplayable the new checks if is displayable
     */
    public void setIsDisplayable(String isDisplayable) {
        this.isDisplayable = isDisplayable;
    }

    /**
     * Sets the checks if is editable.
     *
     * @param isEditable the new checks if is editable
     */
    public void setIsEditable(String isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * Sets the checks if is mandatory.
     *
     * @param isMandatory the new checks if is mandatory
     */
    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    /**
     * Sets the mapping key.
     *
     * @param mappingKey the new mapping key
     */
    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    /**
     * Sets the max occurance.
     *
     * @param maxOccurance the new max occurance
     */
    public void setMaxOccurance(String maxOccurance) {
        this.maxOccurance = maxOccurance;
    }

    /**
     * Sets the pet attribute values.
     *
     * @param petAttributeValues the new pet attribute values
     */
    public void setPetAttributeValues(List<PetAttributeValue> petAttributeValues) {
        this.petAttributeValues = petAttributeValues;
    }

    /**
     * Sets the updated by.
     *
     * @param updatedBy the new updated by
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Sets the updated date.
     *
     * @param updatedDate the new updated date
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

}