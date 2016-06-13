
package com.belk.pep.model;

import java.util.Date;


/**
 * The Class PetsFound.
 *
 * @author AFUSOS3
 */
public class PetsFound implements Comparable<PetsFound>{

    /**
     * Instantiates a new pets found.
     */
    public PetsFound() {
       
    }

    /** The parent style orin. */
    private String parentStyleOrin;
    
    /**omniChannelVendor**/
    String omniChannelVendor;
    
    
    /** The orin number. */
    private String orinNumber;
    
    
    /** The dept id. */
    private String deptId;    
    

    /** The product name. */
    private String productName;
    
    
    /** The entry type. */
    private String entryType;
    

    
    /** The vendor name. */
    private String vendorName;
    
    
    /** The vendor style. */
    private String vendorStyle;
    

    
    /** The image state. */
    private String imageState;
    
    
    /** The content state. */
    private String  contentState;
    
    
    /** The completion date. */
    private String completionDate;
    
    private String colorDesc;
    
    public String getColorDesc() {
        return colorDesc;
    }


    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc;
    }

    /**
     * petStatus
     */
    private String petStatus;
    
    private String supplierId;
    
   




    public String getReq_Type() {
        return req_Type;
    }


    public void setReq_Type(String req_Type) {
        this.req_Type = req_Type;
    }

    private String primaryUPC;
    
    private String classId;
    
    private String req_Type;


    public String getPetStatus() {
        return petStatus;
    }


    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }


    /**
     * Gets the parent style orin.
     *
     * @return the parentStyleOrin
     */
    public String getParentStyleOrin() {
        return parentStyleOrin;
    }


    /**
     * Sets the parent style orin.
     *
     * @param parentStyleOrin the parentStyleOrin to set
     */
    public void setParentStyleOrin(String parentStyleOrin) {
        this.parentStyleOrin = parentStyleOrin;
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



    public String getCompletionDate() {
        return completionDate;
    }


    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PetsFound [parentStyleOrin=" + parentStyleOrin
            + ", orinNumber=" + orinNumber + ", deptId=" + deptId
            + ", productName=" + productName + ", entryType=" + entryType
            + ", vendorName=" + vendorName + ", vendorStyle=" + vendorStyle
            + ", imageState=" + imageState + ", contentState=" + contentState
            + ", completionDate=" + completionDate + "]";
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
        result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
        result =
            prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result =
            prime * result + ((imageState == null) ? 0 : imageState.hashCode());
        result =
            prime * result + ((orinNumber == null) ? 0 : orinNumber.hashCode());
        result =
            prime * result
                + ((parentStyleOrin == null) ? 0 : parentStyleOrin.hashCode());
        result =
            prime * result
                + ((productName == null) ? 0 : productName.hashCode());
        result =
            prime * result + ((vendorName == null) ? 0 : vendorName.hashCode());
        result =
            prime * result
                + ((vendorStyle == null) ? 0 : vendorStyle.hashCode());
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
        PetsFound other = (PetsFound) obj;
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
        if (imageState == null) {
            if (other.imageState != null)
                return false;
        }
        else if (!imageState.equals(other.imageState))
            return false;
        if (orinNumber == null) {
            if (other.orinNumber != null)
                return false;
        }
        else if (!orinNumber.equals(other.orinNumber))
            return false;
        if (parentStyleOrin == null) {
            if (other.parentStyleOrin != null)
                return false;
        }
        else if (!parentStyleOrin.equals(other.parentStyleOrin))
            return false;
        if (productName == null) {
            if (other.productName != null)
                return false;
        }
        else if (!productName.equals(other.productName))
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
        return true;
    }


    public String getOmniChannelVendor() {
        return omniChannelVendor;
    }


    public void setOmniChannelVendor(String omniChannelVendor) {
        this.omniChannelVendor = omniChannelVendor;
    }

    /**
     * Gets the supplier id.
     *
     * @return the supplier id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * Sets the supplier id.
     *
     * @param supplierId
     *            the new supplier id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * Gets the primary upc.
     *
     * @return the primary upc
     */
    public String getPrimaryUPC() {
        return primaryUPC;
    }

    /**
     * Sets the primary upc.
     *
     * @param primaryUPC
     *            the new primary upc
     */
    public void setPrimaryUPC(String primaryUPC) {
        this.primaryUPC = primaryUPC;
    }

    /**
     * Gets the class id.
     *
     * @return the class id
     */
    public String getClassId() {
        return classId;
    }

    /**
     * Sets the class id.
     *
     * @param classId
     *            the new class id
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public int compareTo(PetsFound arg1) {
        
        int value1 = this.getOrinNumber().compareTo(arg1.getOrinNumber());
        
        return value1;
    }
    
    String petStyleState;
    String petImageState;
    String petContentState; 
    String earliestComplitionDate;

    public String getPetStyleState() {
        return petStyleState;
    }


    public void setPetStyleState(String petStyleState) {
        this.petStyleState = petStyleState;
    }


    public String getPetImageState() {
        return petImageState;
    }


    public void setPetImageState(String petImageState) {
        this.petImageState = petImageState;
    }


    public String getPetContentState() {
        return petContentState;
    }


    public void setPetContentState(String petContentState) {
        this.petContentState = petContentState;
    }


    public String getEarliestComplitionDate() {
        return earliestComplitionDate;
    }


    public void setEarliestComplitionDate(String earliestComplitionDate) {
        this.earliestComplitionDate = earliestComplitionDate;
    }
    
    private String existsInGroup;
    private String CFAS;

    /**
     * @return the existsInGroup
     */
    public String getExistsInGroup() {
        return existsInGroup;
    }


    /**
     * @param existsInGroup the existsInGroup to set
     */
    public void setExistsInGroup(String existsInGroup) {
        this.existsInGroup = existsInGroup;
    }


    /**
     * @return the cFAS
     */
    public String getCFAS() {
        return CFAS;
    }


    /**
     * @param cFAS the cFAS to set
     */
    public void setCFAS(String cFAS) {
        CFAS = cFAS;
    }
    
    
}
