
package com.belk.pep.vo;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.belk.pep.model.DropDownContainer;
import com.belk.pep.util.SortComparator;

// TODO: Auto-generated Javadoc
/**
 * The Class BlueMartiniAttributesVO.
 *
 * @author AFUSOS3
 */
public class BlueMartiniAttributesVO implements Comparable<BlueMartiniAttributesVO>{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;


    /** The mdm id. */
    private String mdmId;

    /** The zbm secondary spec value. */
    private String zbmSecondarySpecValue;

    /** The attribute id. */
    private String attributeId;

    /** The category id. */
    private String categoryId;

    /** The attribute name. */
    private String attributeName;

    /** The attribute field type. */
    private String attributeFieldType;

    /** The mapping key. */
    private String mappingKey;

    /** The attribute path. */
    private String attributePath;

    /** The attribute status. */
    private String attributeStatus;

    /** The attribute field value. */
    private String attributeFieldValue;

    /** The attribute type. */
    private String attributeType;

    /** The attribute field value sequence. */
    private String attributeFieldValueSequence;

    /** The display name. */
    private String displayName;

    /** The is displayable. */
    private String isDisplayable;

    /** The is editable. */
    private String isEditable;

    /** The is mandatory. */
    private String isMandatory;

    /** The html description. */
    private String htmlDescription;

    /** The maximum ocurrence. */
    private String maximumOcurrence;

    /** The created by. */
    private String createdBy;

    /** The created date. */
    private String createdDate;

    /** The updated by. */
    private String updatedBy;

    /** The updated date. */
    private String updatedDate;

    /** The map of display fields. */
    Map<String,List<?>> mapOfDisplayFields = new LinkedHashMap<String,List<?>>();

    /** The drop down map. */
    Map<String,List<DropDownContainer>> dropDownValueMap = new LinkedHashMap<String,List<DropDownContainer>>();

    /** The drop down values map. */
	// Changed to LinkedHash Map for the sorting of attributes.
    Map<String,String> dropDownValuesMap =  new TreeMap<String,String>(new SortComparator());

    /** The saved drop down values map. */
    Map<String,String> savedDropDownValuesMap =  new ConcurrentHashMap<String,String>();

    /** The drop down values map. */
    Map<String,String> radioButtonValuesMap =  new ConcurrentHashMap<String,String>();

    /** The saved radio button values map. */
    Map<String,String> savedRadioButtonValuesMap =  new ConcurrentHashMap<String,String>();
    
    /**
     * Instantiates a new blue martini attributes vo.
     */
    public BlueMartiniAttributesVO() {

    }

    /**
     * Gets the attribute field type.
     *
     * @return the attributeFieldType
     */
    public String getAttributeFieldType() {
        return attributeFieldType;
    }

    /**
     * Gets the attribute field value.
     *
     * @return the attributeFieldValue
     */
    public String getAttributeFieldValue() {
        return attributeFieldValue;
    }

    /**
     * Gets the attribute field value sequence.
     *
     * @return the attributeFieldValueSequence
     */
    public String getAttributeFieldValueSequence() {
        return attributeFieldValueSequence;
    }

    /**
     * Gets the attribute id.
     *
     * @return the attributeId
     */
    public String getAttributeId() {
        return attributeId;
    }

    /**
     * Gets the attribute name.
     *
     * @return the attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Gets the attribute path.
     *
     * @return the attributePath
     */
    public String getAttributePath() {
        return attributePath;
    }

    /**
     * Gets the attribute status.
     *
     * @return the attributeStatus
     */
    public String getAttributeStatus() {
        return attributeStatus;
    }

    /**
     * Gets the attribute type.
     *
     * @return the attributeType
     */
    public String getAttributeType() {
        return attributeType;
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
     * Gets the created by.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Gets the display name.
     *
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the drop down value map.
     *
     * @return the dropDownValueMap
     */
    public Map<String, List<DropDownContainer>> getDropDownValueMap() {
        return dropDownValueMap;
    }

    /**
     * Gets the drop down values map.
     *
     * @return the dropDownValuesMap
     */
    public Map<String, String> getDropDownValuesMap() {
        return dropDownValuesMap;
    }

    /**
     * Gets the html description.
     *
     * @return the htmlDescription
     */
    public String getHtmlDescription() {
        return htmlDescription;
    }

    /**
     * Gets the checks if is displayable.
     *
     * @return the isDisplayable
     */
    public String getIsDisplayable() {
        return isDisplayable;
    }

    /**
     * Gets the checks if is editable.
     *
     * @return the isEditable
     */
    public String getIsEditable() {
        return isEditable;
    }

    /**
     * Gets the checks if is mandatory.
     *
     * @return the isMandatory
     */
    public String getIsMandatory() {
        return isMandatory;
    }

    /**
     * Gets the map of display fields.
     *
     * @return the mapOfDisplayFields
     */
    public Map<String, List<?>> getMapOfDisplayFields() {
        return mapOfDisplayFields;
    }

    /**
     * Gets the mapping key.
     *
     * @return the mappingKey
     */
    public String getMappingKey() {
        return mappingKey;
    }

    /**
     * Gets the maximum ocurrence.
     *
     * @return the maximumOcurrence
     */
    public String getMaximumOcurrence() {
        return maximumOcurrence;
    }

    /**
     * Gets the mdm id.
     *
     * @return the mdmId
     */
    public String getMdmId() {
        return mdmId;
    }

    /**
     * Gets the saved drop down values map.
     *
     * @return the savedDropDownValuesMap
     */
    public Map<String, String> getSavedDropDownValuesMap() {
        return savedDropDownValuesMap;
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
     * Gets the updated date.
     *
     * @return the updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }


    /**
     * Gets the zbm secondary spec value.
     *
     * @return the zbmSecondarySpecValue
     */
    public String getZbmSecondarySpecValue() {
        return zbmSecondarySpecValue;
    }


    /**
     * Sets the attribute field type.
     *
     * @param attributeFieldType the attributeFieldType to set
     */
    public void setAttributeFieldType(String attributeFieldType) {
        this.attributeFieldType = attributeFieldType;
    }


    /**
     * Sets the attribute field value.
     *
     * @param attributeFieldValue the attributeFieldValue to set
     */
    public void setAttributeFieldValue(String attributeFieldValue) {
        this.attributeFieldValue = attributeFieldValue;
    }

    /**
     * Sets the attribute field value sequence.
     *
     * @param attributeFieldValueSequence the attributeFieldValueSequence to set
     */
    public void setAttributeFieldValueSequence(String attributeFieldValueSequence) {
        this.attributeFieldValueSequence = attributeFieldValueSequence;
    }

    /**
     * Sets the attribute id.
     *
     * @param attributeId the attributeId to set
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Sets the attribute name.
     *
     * @param attributeName the attributeName to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Sets the attribute path.
     *
     * @param attributePath the attributePath to set
     */
    public void setAttributePath(String attributePath) {
        this.attributePath = attributePath;
    }

    /**
     * Sets the attribute status.
     *
     * @param attributeStatus the attributeStatus to set
     */
    public void setAttributeStatus(String attributeStatus) {
        this.attributeStatus = attributeStatus;
    }

    /**
     * Sets the attribute type.
     *
     * @param attributeType the attributeType to set
     */
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
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
     * Sets the created by.
     *
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Sets the display name.
     *
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the drop down value map.
     *
     * @param dropDownValueMap the dropDownValueMap to set
     */
    public void setDropDownValueMap(
        Map<String, List<DropDownContainer>> dropDownValueMap) {
        this.dropDownValueMap = dropDownValueMap;
    }

    /**
     * Sets the drop down values map.
     *
     * @param dropDownValuesMap the dropDownValuesMap to set
     */
    public void setDropDownValuesMap(Map<String, String> dropDownValuesMap) {
        this.dropDownValuesMap = dropDownValuesMap;
    }

    /**
     * Sets the html description.
     *
     * @param htmlDescription the htmlDescription to set
     */
    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    /**
     * Sets the checks if is displayable.
     *
     * @param isDisplayable the isDisplayable to set
     */
    public void setIsDisplayable(String isDisplayable) {
        this.isDisplayable = isDisplayable;
    }

    /**
     * Sets the checks if is editable.
     *
     * @param isEditable the isEditable to set
     */
    public void setIsEditable(String isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * Sets the checks if is mandatory.
     *
     * @param isMandatory the isMandatory to set
     */
    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    /**
     * Sets the map of display fields.
     *
     * @param mapOfDisplayFields the mapOfDisplayFields to set
     */
    public void setMapOfDisplayFields(Map<String, List<?>> mapOfDisplayFields) {
        this.mapOfDisplayFields = mapOfDisplayFields;
    }

    /**
     * Sets the mapping key.
     *
     * @param mappingKey the mappingKey to set
     */
    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    /**
     * Sets the maximum ocurrence.
     *
     * @param maximumOcurrence the maximumOcurrence to set
     */
    public void setMaximumOcurrence(String maximumOcurrence) {
        this.maximumOcurrence = maximumOcurrence;
    }

    /**
     * Sets the mdm id.
     *
     * @param mdmId the mdmId to set
     */
    public void setMdmId(String mdmId) {
        this.mdmId = mdmId;
    }

    /**
     * Sets the saved drop down values map.
     *
     * @param savedDropDownValuesMap the savedDropDownValuesMap to set
     */
    public void setSavedDropDownValuesMap(Map<String, String> savedDropDownValuesMap) {
        this.savedDropDownValuesMap = savedDropDownValuesMap;
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
     * Sets the updated date.
     *
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }


    /**
     * Sets the zbm secondary spec value.
     *
     * @param zbmSecondarySpecValue the zbmSecondarySpecValue to set
     */
    public void setZbmSecondarySpecValue(String zbmSecondarySpecValue) {
        this.zbmSecondarySpecValue = zbmSecondarySpecValue;
    }


    /**
     * Gets the radio button values map.
     *
     * @return the radio button values map
     */
    public Map<String, String> getRadioButtonValuesMap() {
        return radioButtonValuesMap;
    }

    /**
     * Sets the radio button values map.
     *
     * @param radioButtonValuesMap the radio button values map
     */
    public void setRadioButtonValuesMap(Map<String, String> radioButtonValuesMap) {
        this.radioButtonValuesMap = radioButtonValuesMap;
    }

    /**
     * Gets the saved radio button values map.
     *
     * @return the saved radio button values map
     */
    public Map<String, String> getSavedRadioButtonValuesMap() {
        return savedRadioButtonValuesMap;
    }
    
    /**
     * Sets the saved radio button values map.
     *
     * @param savedRadioButtonValuesMap the saved radio button values map
     */
    public void setSavedRadioButtonValuesMap(
        Map<String, String> savedRadioButtonValuesMap) {
        this.savedRadioButtonValuesMap = savedRadioButtonValuesMap;
    }
    
    
    
    
    @Override
      public int compareTo(BlueMartiniAttributesVO obj) {
        int value = 0;
        if(null != obj && obj.getDisplayName() != null && this.getDisplayName() != null)
        {
           // value = obj.getDisplayName().compareTo(this.getDisplayName());
            value = this.getDisplayName().compareTo(obj.getDisplayName());
        }
        return value;
    }
    
	 public String toString(){
        return this.getDisplayName()+ " " +this.getAttributeFieldType() ;
        
    }

}