
package com.belk.pep.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class Style.
 *
 * @author AFUSOS3
 */
public class StyleObj {

   
    /**
     * Instantiates a new style.
     */
    public StyleObj() {
        
    }
    

    /** The orin number. */
    String orinNumber;
    

    /** The parent style orin number. */
    String parentStyleOrinNumber;
    

    /** The dept id. */
    String deptId;
    
    /** The entry type. */
    String entryType;
    
    /** The group number. */
    String groupNumber;    
    
    /** The department number. */
    String departmentNumber;
    
    /** The vendor name. */
    String vendorName;
    
    /** The vendor style number. */
    String vendorStyleNumber;
    
    /** The vendor style. */
    String vendorStyle;
    

    /** The department id. */
    String departmentId;
    
    /** The image state. */
    String imageState;
    
    /** The content state. */
    String contentState;
    
    /**
     * Gets the department id.
     *
     * @return the departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department id.
     *
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the content state.
     *
     * @return the contentState
     */
    public String getContentState() {
        return contentState;
    }

    /**
     * Sets the content state.
     *
     * @param contentState the contentState to set
     */
    public void setContentState(String contentState) {
        this.contentState = contentState;
    }

    /**
     * Gets the vendor style.
     *
     * @return the vendorStyle
     */
    public String getVendorStyle() {
        return vendorStyle;
    }

    /**
     * Sets the vendor style.
     *
     * @param vendorStyle the vendorStyle to set
     */
    public void setVendorStyle(String vendorStyle) {
        this.vendorStyle = vendorStyle;
    }


    /** The product name. */
    String productName;
    
    /** The content status. */
    String contentStatus;
    
    /** The image status. */
    String imageStatus;
    
    /** The completion date. */
    Date completionDate;
    
    /** The style color. */
    List<StyleColor> styleColor = new ArrayList<StyleColor>();
    
    /**
     * Gets the style color.
     *
     * @return the styleColor
     */
    public List<StyleColor> getStyleColor() {
        return styleColor;
    }

    /**
     * Sets the style color.
     *
     * @param styleColor the styleColor to set
     */
    public void setStyleColor(List<StyleColor> styleColor) {
        this.styleColor = styleColor;
    }

    /**
     * Gets the orin number.
     *
     * @return the orinNumber
     */
    public String getOrinNumber() {
        return orinNumber;
    }

    /**
     * Sets the orin number.
     *
     * @param orinNumber the orinNumber to set
     */
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }

    /**
     * Gets the parent style orin number.
     *
     * @return the parentStyleOrinNumber
     */
    public String getParentStyleOrinNumber() {
        return parentStyleOrinNumber;
    }

    /**
     * Sets the parent style orin number.
     *
     * @param parentStyleOrinNumber the parentStyleOrinNumber to set
     */
    public void setParentStyleOrinNumber(String parentStyleOrinNumber) {
        this.parentStyleOrinNumber = parentStyleOrinNumber;
    }


    /**
     * Gets the orin.
     *
     * @return the orin
     */
    public String getOrin() {
        return orinNumber;
    }

    /**
     * Sets the orin.
     *
     * @param orin the orin to set
     */
    public void setOrin(String orin) {
        orinNumber = orin;
    }

    /**
     * Gets the group number.
     *
     * @return the groupNumber
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Sets the group number.
     *
     * @param groupNumber the groupNumber to set
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Gets the department number.
     *
     * @return the departmentNumber
     */
    public String getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * Sets the department number.
     *
     * @param departmentNumber the departmentNumber to set
     */
    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    /**
     * Gets the vendor name.
     *
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Sets the vendor name.
     *
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Gets the vendor style number.
     *
     * @return the vendorStyleNumber
     */
    public String getVendorStyleNumber() {
        return vendorStyleNumber;
    }

    /**
     * Sets the vendor style number.
     *
     * @param vendorStyleNumber the vendorStyleNumber to set
     */
    public void setVendorStyleNumber(String vendorStyleNumber) {
        this.vendorStyleNumber = vendorStyleNumber;
    }

    /**
     * Gets the product name.
     *
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the content status.
     *
     * @return the contentStatus
     */
    public String getContentStatus() {
        return contentStatus;
    }

    /**
     * Sets the content status.
     *
     * @param contentStatus the contentStatus to set
     */
    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    /**
     * Gets the image status.
     *
     * @return the imageStatus
     */
    public String getImageStatus() {
        return imageStatus;
    }

    /**
     * Sets the image status.
     *
     * @param imageStatus the imageStatus to set
     */
    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    /**
     * Gets the completion date.
     *
     * @return the completionDate
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date.
     *
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Gets the style orin.
     *
     * @return the styleOrin
     */
    public String getStyleOrin() {
        return parentStyleOrinNumber;
    }

   

    /**
     * Gets the dept id.
     *
     * @return the deptId
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * Sets the dept id.
     *
     * @param deptId the deptId to set
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * Gets the entry type.
     *
     * @return the entryType
     */
    public String getEntryType() {
        return entryType;
    }

    /**
     * Sets the entry type.
     *
     * @param entryType the entryType to set
     */
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
    
    /**
     * Gets the image state.
     *
     * @return the imageState
     */
    public String getImageState() {
        return imageState;
    }

    /**
     * Sets the image state.
     *
     * @param imageState the imageState to set
     */
    public void setImageState(String imageState) {
        this.imageState = imageState;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Style [orinNumber=" + orinNumber + ", parentStyleOrinNumber="
            + parentStyleOrinNumber + ", deptId=" + deptId + ", entryType="
            + entryType + ", groupNumber=" + groupNumber
            + ", departmentNumber=" + departmentNumber + ", vendorName="
            + vendorName + ", vendorStyleNumber=" + vendorStyleNumber
            + ", vendorStyle=" + vendorStyle + ", departmentId=" + departmentId
            + ", imageState=" + imageState + ", productName=" + productName
            + ", contentStatus=" + contentStatus + ", imageStatus="
            + imageStatus + ", completionDate=" + completionDate
            + ", styleColor=" + styleColor + "]";
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
                + ((completionDate == null) ? 0 : completionDate.hashCode());
        result =
            prime * result
                + ((contentState == null) ? 0 : contentState.hashCode());
        result =
            prime * result
                + ((contentStatus == null) ? 0 : contentStatus.hashCode());
        result =
            prime * result
                + ((departmentId == null) ? 0 : departmentId.hashCode());
        result =
            prime
                * result
                + ((departmentNumber == null) ? 0 : departmentNumber.hashCode());
        result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
        result =
            prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result =
            prime * result
                + ((groupNumber == null) ? 0 : groupNumber.hashCode());
        result =
            prime * result + ((imageState == null) ? 0 : imageState.hashCode());
        result =
            prime * result
                + ((imageStatus == null) ? 0 : imageStatus.hashCode());
        result =
            prime * result + ((orinNumber == null) ? 0 : orinNumber.hashCode());
        result =
            prime
                * result
                + ((parentStyleOrinNumber == null) ? 0 : parentStyleOrinNumber
                    .hashCode());
        result =
            prime * result
                + ((productName == null) ? 0 : productName.hashCode());
        result =
            prime * result + ((styleColor == null) ? 0 : styleColor.hashCode());
        result =
            prime * result + ((vendorName == null) ? 0 : vendorName.hashCode());
        result =
            prime * result
                + ((vendorStyle == null) ? 0 : vendorStyle.hashCode());
        result =
            prime
                * result
                + ((vendorStyleNumber == null) ? 0 : vendorStyleNumber
                    .hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StyleObj other = (StyleObj) obj;
        if (completionDate == null) {
            if (other.completionDate != null)
                return false;
        }
        else if (!completionDate.equals(other.completionDate))
            return false;
        if (contentState == null) {
            if (other.contentState != null)
                return false;
        }
        else if (!contentState.equals(other.contentState))
            return false;
        if (contentStatus == null) {
            if (other.contentStatus != null)
                return false;
        }
        else if (!contentStatus.equals(other.contentStatus))
            return false;
        if (departmentId == null) {
            if (other.departmentId != null)
                return false;
        }
        else if (!departmentId.equals(other.departmentId))
            return false;
        if (departmentNumber == null) {
            if (other.departmentNumber != null)
                return false;
        }
        else if (!departmentNumber.equals(other.departmentNumber))
            return false;
        if (deptId == null) {
            if (other.deptId != null)
                return false;
        }
        else if (!deptId.equals(other.deptId))
            return false;
        if (entryType == null) {
            if (other.entryType != null)
                return false;
        }
        else if (!entryType.equals(other.entryType))
            return false;
        if (groupNumber == null) {
            if (other.groupNumber != null)
                return false;
        }
        else if (!groupNumber.equals(other.groupNumber))
            return false;
        if (imageState == null) {
            if (other.imageState != null)
                return false;
        }
        else if (!imageState.equals(other.imageState))
            return false;
        if (imageStatus == null) {
            if (other.imageStatus != null)
                return false;
        }
        else if (!imageStatus.equals(other.imageStatus))
            return false;
        if (orinNumber == null) {
            if (other.orinNumber != null)
                return false;
        }
        else if (!orinNumber.equals(other.orinNumber))
            return false;
        if (parentStyleOrinNumber == null) {
            if (other.parentStyleOrinNumber != null)
                return false;
        }
        else if (!parentStyleOrinNumber.equals(other.parentStyleOrinNumber))
            return false;
        if (productName == null) {
            if (other.productName != null)
                return false;
        }
        else if (!productName.equals(other.productName))
            return false;
        if (styleColor == null) {
            if (other.styleColor != null)
                return false;
        }
        else if (!styleColor.equals(other.styleColor))
            return false;
        if (vendorName == null) {
            if (other.vendorName != null)
                return false;
        }
        else if (!vendorName.equals(other.vendorName))
            return false;
        if (vendorStyle == null) {
            if (other.vendorStyle != null)
                return false;
        }
        else if (!vendorStyle.equals(other.vendorStyle))
            return false;
        if (vendorStyleNumber == null) {
            if (other.vendorStyleNumber != null)
                return false;
        }
        else if (!vendorStyleNumber.equals(other.vendorStyleNumber))
            return false;
        return true;
    }


   
   
    
    

}
