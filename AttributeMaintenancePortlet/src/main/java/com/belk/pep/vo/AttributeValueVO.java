package com.belk.pep.vo;

import java.util.List;

/**
 * The Class AttributeValueVO.
 */
public class AttributeValueVO {
    
    /** The attribute id. */
    private String attributeId;
    
    /** The category id. */
    private String categoryId;
    
    /** The attribute name. */
    private String attributeName;
    
    /** The attribute display name. */
    private String attributeDisplayName;
    
    /** The attribute status. */
    private String attributeStatus;
    
    /** The is required. */
    private String isRequired;
    
    /** The is searchable. */
    private String isSearchable;
    
    /** The is editable. */
    private String isEditable;
    
    /** The attribute type. */
    private String attributeFieldType;
    
    /** The attribute field value. */
    private String attributeFieldValue;
    
    /** The attribute value list. */
    private List<AttributeValueListVO> attributeValueList;
    
    /** The radio first value. */
    private String radioFirstValue;
    
    /** The radio second value. */
    private String radioSecondValue;
    
    /** The checkbox first value. */
    private String checkboxFirstValue;
    
    /** The checkbox second value. */
    private String checkboxSecondValue;
    
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
     * Gets the category id.
     *
     * @return the category id
     */
    public String getCategoryId() {
        return categoryId;
    }
    
    /**
     * Sets the category id.
     *
     * @param categoryId the new category id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
     * Gets the attribute value list.
     *
     * @return the attribute value list
     */
    public List<AttributeValueListVO> getAttributeValueList() {
        return attributeValueList;
    }

    /**
     * Sets the attribute value list.
     *
     * @param attributeValueList the new attribute value list
     */
    public void setAttributeValueList(List<AttributeValueListVO> attributeValueList) {
        this.attributeValueList = attributeValueList;
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
     * Gets the attribute display name.
     *
     * @return the attribute display name
     */
    public String getAttributeDisplayName() {
        return attributeDisplayName;
    }

    /**
     * Sets the attribute display name.
     *
     * @param attributeDisplayName the new attribute display name
     */
    public void setAttributeDisplayName(String attributeDisplayName) {
        this.attributeDisplayName = attributeDisplayName;
    }

    /**
     * Gets the radio first value.
     *
     * @return the radio first value
     */
    public String getRadioFirstValue() {
        return radioFirstValue;
    }

    /**
     * Sets the radio first value.
     *
     * @param radioFirstValue the new radio first value
     */
    public void setRadioFirstValue(String radioFirstValue) {
        this.radioFirstValue = radioFirstValue;
    }

    /**
     * Gets the radio second value.
     *
     * @return the radio second value
     */
    public String getRadioSecondValue() {
        return radioSecondValue;
    }

    /**
     * Sets the radio second value.
     *
     * @param radioSecondValue the new radio second value
     */
    public void setRadioSecondValue(String radioSecondValue) {
        this.radioSecondValue = radioSecondValue;
    }

    /**
     * Gets the check box first value.
     *
     * @return the check box first value
     */
    public String getCheckboxFirstValue() {
        return checkboxFirstValue;
    }

    /**
     * Sets the check box first value.
     *
     * @param checkboxFirstValue the new check box first value
     */
    public void setCheckboxFirstValue(String checkboxFirstValue) {
        this.checkboxFirstValue = checkboxFirstValue;
    }

    /**
     * Gets the check box second value.
     *
     * @return the check box second value
     */
    public String getCheckboxSecondValue() {
        return checkboxSecondValue;
    }

    /**
     * Sets the checkbox second value.
     *
     * @param checkboxSecondValue the new checkbox second value
     */
    public void setCheckboxSecondValue(String checkboxSecondValue) {
        this.checkboxSecondValue = checkboxSecondValue;
    }
    
}
