package com.belk.pep.vo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.belk.pep.model.DropDownContainer;

/**
 * The Class PetAttributeVO.
 *
 * @author AFUSOS3
 */
public class PetAttributeVO implements Serializable,Comparable<PetAttributeVO>{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The mdm id. */
    private String mdmId;

    private String secondarySpecValue;

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

    private String attributeFieldValue;

    private String attributeType;

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
    Map<String,String> dropDownValuesMap =  new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);

    /** The drop down values map. */
    Map<String,String> radioButtonValuesMap =  new ConcurrentHashMap<String,String>();


    /** The saved drop down values map. */
    Map<String,String> savedDropDownValuesMap =  new ConcurrentHashMap<String,String>();

    /** The saved radio button values map. */
    Map<String,String> savedRadioButtonValuesMap =  new ConcurrentHashMap<String,String>();


    /**
     * Instantiates a new pet attribute vo.
     */
    public PetAttributeVO() {

    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PetAttributeVO other = (PetAttributeVO) obj;
        if (attributeFieldType == null) {
            if (other.attributeFieldType != null) {
                return false;
            }
        }
        else if (!attributeFieldType.equals(other.attributeFieldType)) {
            return false;
        }
        if (attributeId == null) {
            if (other.attributeId != null) {
                return false;
            }
        }
        else if (!attributeId.equals(other.attributeId)) {
            return false;
        }
        if (attributeName == null) {
            if (other.attributeName != null) {
                return false;
            }
        }
        else if (!attributeName.equals(other.attributeName)) {
            return false;
        }
        if (attributePath == null) {
            if (other.attributePath != null) {
                return false;
            }
        }
        else if (!attributePath.equals(other.attributePath)) {
            return false;
        }
        if (attributeStatus == null) {
            if (other.attributeStatus != null) {
                return false;
            }
        }
        else if (!attributeStatus.equals(other.attributeStatus)) {
            return false;
        }
        if (categoryId == null) {
            if (other.categoryId != null) {
                return false;
            }
        }
        else if (!categoryId.equals(other.categoryId)) {
            return false;
        }
        if (createdBy == null) {
            if (other.createdBy != null) {
                return false;
            }
        }
        else if (!createdBy.equals(other.createdBy)) {
            return false;
        }
        if (createdDate == null) {
            if (other.createdDate != null) {
                return false;
            }
        }
        else if (!createdDate.equals(other.createdDate)) {
            return false;
        }
        if (displayName == null) {
            if (other.displayName != null) {
                return false;
            }
        }
        else if (!displayName.equals(other.displayName)) {
            return false;
        }
        if (htmlDescription == null) {
            if (other.htmlDescription != null) {
                return false;
            }
        }
        else if (!htmlDescription.equals(other.htmlDescription)) {
            return false;
        }
        if (isDisplayable == null) {
            if (other.isDisplayable != null) {
                return false;
            }
        }
        else if (!isDisplayable.equals(other.isDisplayable)) {
            return false;
        }
        if (isEditable == null) {
            if (other.isEditable != null) {
                return false;
            }
        }
        else if (!isEditable.equals(other.isEditable)) {
            return false;
        }
        if (isMandatory == null) {
            if (other.isMandatory != null) {
                return false;
            }
        }
        else if (!isMandatory.equals(other.isMandatory)) {
            return false;
        }
        if (mappingKey == null) {
            if (other.mappingKey != null) {
                return false;
            }
        }
        else if (!mappingKey.equals(other.mappingKey)) {
            return false;
        }
        if (maximumOcurrence == null) {
            if (other.maximumOcurrence != null) {
                return false;
            }
        }
        else if (!maximumOcurrence.equals(other.maximumOcurrence)) {
            return false;
        }
        if (updatedBy == null) {
            if (other.updatedBy != null) {
                return false;
            }
        }
        else if (!updatedBy.equals(other.updatedBy)) {
            return false;
        }
        if (updatedDate == null) {
            if (other.updatedDate != null) {
                return false;
            }
        }
        else if (!updatedDate.equals(other.updatedDate)) {
            return false;
        }
        return true;
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
     * @return the attributeFieldValue
     */
    public String getAttributeFieldValue() {
        return attributeFieldValue;
    }

    /**
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
     * @return the dropDownValueMap
     */
    public Map<String, List<DropDownContainer>> getDropDownValueMap() {
        return dropDownValueMap;
    }

    /**
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
     * @return the radioButtonValuesMap
     */
    public Map<String, String> getRadioButtonValuesMap() {
        return radioButtonValuesMap;
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
     * @return the savedRadioButtonValuesMap
     */
    public Map<String, String> getSavedRadioButtonValuesMap() {
        return savedRadioButtonValuesMap;
    }

    /**
     * Gets the secondary spec value.
     *
     * @return the secondarySpecValue
     */
    public String getSecondarySpecValue() {
        return secondarySpecValue;
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                (prime
                        * result)
                        + (attributeFieldType == null ? 0 : attributeFieldType
                            .hashCode());
        result =
                (prime * result)
                + (attributeId == null ? 0 : attributeId.hashCode());
        result =
                (prime * result)
                + (attributeName == null ? 0 : attributeName.hashCode());
        result =
                (prime * result)
                + (attributePath == null ? 0 : attributePath.hashCode());
        result =
                (prime * result)
                + (attributeStatus == null ? 0 : attributeStatus.hashCode());
        result =
                (prime * result) + (categoryId == null ? 0 : categoryId.hashCode());
        result =
                (prime * result) + (createdBy == null ? 0 : createdBy.hashCode());
        result =
                (prime * result)
                + (createdDate == null ? 0 : createdDate.hashCode());
        result =
                (prime * result)
                + (displayName == null ? 0 : displayName.hashCode());
        result =
                (prime * result)
                + (htmlDescription == null ? 0 : htmlDescription.hashCode());
        result =
                (prime * result)
                + (isDisplayable == null ? 0 : isDisplayable.hashCode());
        result =
                (prime * result) + (isEditable == null ? 0 : isEditable.hashCode());
        result =
                (prime * result)
                + (isMandatory == null ? 0 : isMandatory.hashCode());
        result =
                (prime * result) + (mappingKey == null ? 0 : mappingKey.hashCode());
        result =
                (prime
                        * result)
                        + (maximumOcurrence == null ? 0 : maximumOcurrence.hashCode());
        result =
                (prime * result) + (updatedBy == null ? 0 : updatedBy.hashCode());
        result =
                (prime * result)
                + (updatedDate == null ? 0 : updatedDate.hashCode());
        return result;
    }

    /**
     * Sets the attribute field type.
     *
     * @param attributeFieldType            the attributeFieldType to set
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
     * @param attributeId            the attributeId to set
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
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
     * @param categoryId the new category id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
    public void setCreatedDate(String createdDate) {
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
     * @param htmlDescription the new html description
     */
    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
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
     * @param mappingKey the new mapping key
     */
    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    /**
     * Sets the maximum ocurrence.
     *
     * @param maximumOcurrence the new maximum ocurrence
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
     * @param radioButtonValuesMap the radioButtonValuesMap to set
     */
    public void setRadioButtonValuesMap(Map<String, String> radioButtonValuesMap) {
        this.radioButtonValuesMap = radioButtonValuesMap;
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
     * @param savedRadioButtonValuesMap the savedRadioButtonValuesMap to set
     */
    public void setSavedRadioButtonValuesMap(
        Map<String, String> savedRadioButtonValuesMap) {
        this.savedRadioButtonValuesMap = savedRadioButtonValuesMap;
    }

    /**
     * Sets the secondary spec value.
     *
     * @param secondarySpecValue the secondarySpecValue to set
     */
    public void setSecondarySpecValue(String secondarySpecValue) {
        this.secondarySpecValue = secondarySpecValue;
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
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PetAttributeVO [attributeId=" + attributeId + ", categoryId="
                + categoryId + ", attributeName=" + attributeName
                + ", attributeFieldType=" + attributeFieldType + ", mappingKey="
                + mappingKey + ", attributePath=" + attributePath
                + ", attributeStatus=" + attributeStatus + ", displayName="
                + displayName + ", isDisplayable=" + isDisplayable
                + ", isEditable=" + isEditable + ", isMandatory=" + isMandatory
                + ", htmlDescription=" + htmlDescription + ", maximumOcurrence="
                + maximumOcurrence + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + "]";
    }
    @Override
    public int compareTo(PetAttributeVO obj) {
        String mandatoryObj = obj.getIsMandatory();
        String mandatoryObjCurrent = this.getIsMandatory();
        
        int compVal = mandatoryObj.compareTo(mandatoryObjCurrent);
 
        if (compVal != 0) {
           return compVal;
        } else {
              String nameObj = this.getDisplayName();
              String nameObjCurrent = obj.getDisplayName();
              return nameObj.compareTo(nameObjCurrent);
        }
    }



}
