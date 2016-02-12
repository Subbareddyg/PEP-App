/**
 *
 */
package com.belk.pep.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class DropDownContainer.
 *
 * @author AFUSOS3
 */
public class DropDownContainer implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The attribute id. */
    private String attributeId;

    /** The name. */
    private String name;

    private String value;


    /** The drop down map. */
    Map<String,String> dropDownValueMap = new LinkedHashMap<String,String>();


    /**
     * Instantiates a new drop down container.
     */
    public DropDownContainer() {

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
        final DropDownContainer other = (DropDownContainer) obj;
        if (attributeId == null) {
            if (other.attributeId != null) {
                return false;
            }
        }
        else if (!attributeId.equals(other.attributeId)) {
            return false;
        }
        if (dropDownValueMap == null) {
            if (other.dropDownValueMap != null) {
                return false;
            }
        }
        else if (!dropDownValueMap.equals(other.dropDownValueMap)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }


    /**
     * @return the attributeId
     */
    public String getAttributeId() {
        return attributeId;
    }


    /**
     * Gets the drop down value map.
     *
     * @return the dropDownValueMap
     */
    public Map<String, String> getDropDownValueMap() {
        return dropDownValueMap;
    }


    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result
                + (attributeId == null ? 0 : attributeId.hashCode());
        result =
                prime
                * result
                + (dropDownValueMap == null ? 0 : dropDownValueMap.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
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
     * Sets the drop down value map.
     *
     * @param dropDownValueMap the dropDownValueMap to set
     */
    public void setDropDownValueMap(Map<String, String> dropDownValueMap) {
        this.dropDownValueMap = dropDownValueMap;
    }


    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DropDownContainer [attributeId=" + attributeId + ", name="
                + name + ", dropDownValueMap=" + dropDownValueMap + "]";
    }



}
