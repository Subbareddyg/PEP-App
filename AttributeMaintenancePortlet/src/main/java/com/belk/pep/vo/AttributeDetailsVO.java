package com.belk.pep.vo;

import java.io.Serializable;

/**
 * The Class AttributeDetailsVO.
 */
public class AttributeDetailsVO implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The attribute id. */
    private String attributeId;
    
    /** The catagory id. */
    private String catagoryId;
    
    /** The category name. */
    private String categoryName;
    
    /** The attribute name. */
    private String attributeName;
    
    /** The mapping key. */
    private String mappingKey;
    
    /** The attribute path. */
    private String attributePath;
    
    /** The attribute status. */
    private String attributeStatus;
    
    /** The is editable. */
    private String isEditable;
    
    /** The is searchable. */
    private String isSearchable;
    
    /** The is required. */
    private String isRequired;
    
    /** The attribute field type. */
    private String attributeFieldType;
    
    /** The attribute field value. */
    private String attributeFieldValue;
    
    /** The attribute field value seq. */
    private String attributeFieldValueSeq;
    
    /**
     * Gets the attribute field type.
     *
     * @return the attribute field type
     */
    public String getAttributeFieldType() {
        return attributeFieldType;
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
     * Gets the attribute field value.
     *
     * @return the attribute field value
     */
    public String getAttributeFieldValue() {
        return attributeFieldValue;
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
     * Gets the serialversionuid.
     *
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * Gets the attribute id.
     *
     * @return the attribute id
     */
    public String getAttributeId() {
        return attributeId;
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
     * Gets the catagory id.
     *
     * @return the catagory id
     */
    public String getCatagoryId() {
        return catagoryId;
    }
    
    /**
     * Sets the catagory id.
     *
     * @param catagoryId the new catagory id
     */
    public void setCatagoryId(String catagoryId) {
        this.catagoryId = catagoryId;
    }
    
    /**
     * Gets the attribute name.
     *
     * @return the attribute name
     */
    public String getAttributeName() {
        return attributeName;
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
     * Gets the mapping key.
     *
     * @return the mapping key
     */
    public String getMappingKey() {
        return mappingKey;
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
     * Gets the attribute path.
     *
     * @return the attribute path
     */
    public String getAttributePath() {
        return attributePath;
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
     * Gets the attribute status.
     *
     * @return the attribute status
     */
    public String getAttributeStatus() {
        return attributeStatus;
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
     * Gets the checks if is editable.
     *
     * @return the checks if is editable
     */
    public String getIsEditable() {
        return isEditable;
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
     * Gets the checks if is searchable.
     *
     * @return the checks if is searchable
     */
    public String getIsSearchable() {
        return isSearchable;
    }

    /**
     * Sets the checks if is searchable.
     *
     * @param isSearchable the new checks if is searchable
     */
    public void setIsSearchable(String isSearchable) {
        this.isSearchable = isSearchable;
    }

    /**
     * Gets the checks if is required.
     *
     * @return the checks if is required
     */
    public String getIsRequired() {
        return isRequired;
    }

    /**
     * Sets the checks if is required.
     *
     * @param isRequired the new checks if is required
     */
    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Gets the attribute field value seq.
     *
     * @return the attribute field value seq
     */
    public String getAttributeFieldValueSeq() {
        return attributeFieldValueSeq;
    }

    /**
     * Sets the attribute field value seq.
     *
     * @param attributeFieldValueSeq the new attribute field value seq
     */
    public void setAttributeFieldValueSeq(String attributeFieldValueSeq) {
        this.attributeFieldValueSeq = attributeFieldValueSeq;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    

}
