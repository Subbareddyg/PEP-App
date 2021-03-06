
package com.belk.pep.vo;

import java.io.Serializable;


/**
 * The Class PetAttributeValueVO.
 *
 * @author AFUSOS3
 */
public class PetAttributeValueVO  implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The attribute id. */
    private String attributeId;

    /** The category id. */
    private String categoryId;

    /** The attribute field value sequence. */
    private String attributeFieldValueSequence;

    /** The attribute field value. */
    private String attributeFieldValue;

    /** The created by. */
    private String  createdBy;

    /** The created date. */
    private String  createdDate;

    /** The updated by. */
    private String  updatedBy;

    /** The updated date. */
    private String  updatedDate;

    /**
     * Instantiates a new pet attribute value vo.
     */
    public PetAttributeValueVO() {

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
        final PetAttributeValueVO other = (PetAttributeValueVO) obj;
        if (attributeFieldValue == null) {
            if (other.attributeFieldValue != null) {
                return false;
            }
        }
        else if (!attributeFieldValue.equals(other.attributeFieldValue)) {
            return false;
        }
        if (attributeFieldValueSequence == null) {
            if (other.attributeFieldValueSequence != null) {
                return false;
            }
        }
        else if (!attributeFieldValueSequence
                .equals(other.attributeFieldValueSequence)) {
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
                prime
                * result
                + (attributeFieldValue == null ? 0 : attributeFieldValue
                    .hashCode());
        result =
                prime
                * result
                + (attributeFieldValueSequence == null ? 0
                    : attributeFieldValueSequence.hashCode());
        result =
                prime * result
                + (attributeId == null ? 0 : attributeId.hashCode());
        result =
                prime * result + (categoryId == null ? 0 : categoryId.hashCode());
        result =
                prime * result + (createdBy == null ? 0 : createdBy.hashCode());
        result =
                prime * result
                + (createdDate == null ? 0 : createdDate.hashCode());
        result =
                prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
        result =
                prime * result
                + (updatedDate == null ? 0 : updatedDate.hashCode());
        return result;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PetAttributeValueVO [attributeId=" + attributeId
                + ", categoryId=" + categoryId + ", attributeFieldValueSequence="
                + attributeFieldValueSequence + ", attributeFieldValue="
                + attributeFieldValue + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + "]";
    }



}
