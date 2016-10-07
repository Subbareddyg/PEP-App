
package com.belk.pep.model;

import java.util.List;

import com.belk.pep.vo.OmniSizeVO;

/**
 * The Class PetsFound.
 *
 * @author AFUSOS3
 */
public class PetsFound {

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


    private String  petState;

    /** The completion date. */
    private String completionDate;




    /** The color code. */
    private String colorCode;


    /** The color. */
    private String color;


    /** The vendor size. */
    private String  vendorSize;

    /** The omni size description. */
    private List<OmniSizeVO>  omniSizeDescriptionList;
    
    /** The selected omni size code. */
    private String selectedOmniSizeCode;

    /**
     * Instantiates a new pets found.
     */
    public PetsFound() {

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
        PetsFound other = (PetsFound) obj;
        if (color == null) {
            if (other.color != null) {
                return false;
            }
        }
        else if (!color.equals(other.color)) {
            return false;
        }
        if (colorCode == null) {
            if (other.colorCode != null) {
                return false;
            }
        }
        else if (!colorCode.equals(other.colorCode)) {
            return false;
        }
        if (completionDate == null) {
            if (other.completionDate != null) {
                return false;
            }
        }
        else if (!completionDate.equals(other.completionDate)) {
            return false;
        }
        if (contentState == null) {
            if (other.contentState != null) {
                return false;
            }
        }
        else if (!contentState.equals(other.contentState)) {
            return false;
        }
        if (deptId == null) {
            if (other.deptId != null) {
                return false;
            }
        }
        else if (!deptId.equals(other.deptId)) {
            return false;
        }
        if (entryType == null) {
            if (other.entryType != null) {
                return false;
            }
        }
        else if (!entryType.equals(other.entryType)) {
            return false;
        }
        if (imageState == null) {
            if (other.imageState != null) {
                return false;
            }
        }
        else if (!imageState.equals(other.imageState)) {
            return false;
        }
        if (omniChannelVendor == null) {
            if (other.omniChannelVendor != null) {
                return false;
            }
        }
        else if (!omniChannelVendor.equals(other.omniChannelVendor)) {
            return false;
        }
        if (omniSizeDescriptionList == null) {
            if (other.omniSizeDescriptionList != null) {
                return false;
            }
        }
        else if (omniSizeDescriptionList!=null && other.omniSizeDescriptionList!=null
                && omniSizeDescriptionList.size()!=other.omniSizeDescriptionList.size()) {
            return false;
        }
        else {
            for (OmniSizeVO size: omniSizeDescriptionList) {
                if (!other.omniSizeDescriptionList.contains(size)) {
                    return false;
                }
            }
        }
        if (orinNumber == null) {
            if (other.orinNumber != null) {
                return false;
            }
        }
        else if (!orinNumber.equals(other.orinNumber)) {
            return false;
        }
        if (parentStyleOrin == null) {
            if (other.parentStyleOrin != null) {
                return false;
            }
        }
        else if (!parentStyleOrin.equals(other.parentStyleOrin)) {
            return false;
        }
        if (productName == null) {
            if (other.productName != null) {
                return false;
            }
        }
        else if (!productName.equals(other.productName)) {
            return false;
        }
        if (vendorName == null) {
            if (other.vendorName != null) {
                return false;
            }
        }
        else if (!vendorName.equals(other.vendorName)) {
            return false;
        }
        if (vendorSize == null) {
            if (other.vendorSize != null) {
                return false;
            }
        }
        else if (!vendorSize.equals(other.vendorSize)) {
            return false;
        }
        if (vendorStyle == null) {
            if (other.vendorStyle != null) {
                return false;
            }
        }
        else if (!vendorStyle.equals(other.vendorStyle)) {
            return false;
        }
        return true;
    }


    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }




    /**
     * Gets the color code.
     *
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }


    /**
     * Gets the completion date.
     *
     * @return the completion date
     */
    public String getCompletionDate() {
        return completionDate;
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
     * Gets the dept id.
     *
     * @return the deptId
     */
    public String getDeptId() {
        return deptId;
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
     * Gets the image state.
     *
     * @return the imageState
     */
    public String getImageState() {
        return imageState;
    }


    /**
     * Gets the omni channel vendor.
     *
     * @return the omni channel vendor
     */
    public String getOmniChannelVendor() {
        return omniChannelVendor;
    }


    /**
     * Gets the omni size description.
     *
     * @return the omniSizeDescription
     */
    public List<OmniSizeVO> getOmniSizeDescriptionList() {
        return omniSizeDescriptionList;
    }


    /**
     * Gets the selected omnisizecode.
     * @return
     */
    public String getSelectedOmniSizeCode() {
        return selectedOmniSizeCode;
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
     * Gets the parent style orin.
     *
     * @return the parentStyleOrin
     */
    public String getParentStyleOrin() {
        return parentStyleOrin;
    }


    /**
     * @return the petState
     */
    public String getPetState() {
        return petState;
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
     * Gets the vendor name.
     *
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }


    /**
     * Gets the vendor size.
     *
     * @return the vendorSize
     */
    public String getVendorSize() {
        return vendorSize;
    }


    /**
     * Gets the vendor style.
     *
     * @return the vendorStyle
     */
    public String getVendorStyle() {
        return vendorStyle;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((color == null) ? 0 : color.hashCode());
        result =
                (prime * result) + ((colorCode == null) ? 0 : colorCode.hashCode());
        result =
                (prime * result)
                + ((completionDate == null) ? 0 : completionDate.hashCode());
        result =
                (prime * result)
                + ((contentState == null) ? 0 : contentState.hashCode());
        result = (prime * result) + ((deptId == null) ? 0 : deptId.hashCode());
        result =
                (prime * result) + ((entryType == null) ? 0 : entryType.hashCode());
        result =
                (prime * result) + ((imageState == null) ? 0 : imageState.hashCode());
        result =
                (prime * result)
                        + ((omniChannelVendor == null) ? 0 : omniChannelVendor.hashCode());
        result =
                (prime * result)
                        + ((omniSizeDescriptionList == null) ? 0 : omniSizeDescriptionList.hashCode());
        result =
                (prime * result)
                        + ((selectedOmniSizeCode == null) ? 0 : selectedOmniSizeCode.hashCode());
        result =
                (prime * result) + ((orinNumber == null) ? 0 : orinNumber.hashCode());
        result =
                (prime * result)
                + ((parentStyleOrin == null) ? 0 : parentStyleOrin.hashCode());
        result =
                (prime * result)
                + ((productName == null) ? 0 : productName.hashCode());
        result =
                (prime * result) + ((vendorName == null) ? 0 : vendorName.hashCode());
        result =
                (prime * result) + ((vendorSize == null) ? 0 : vendorSize.hashCode());
        result =
                (prime * result)
                + ((vendorStyle == null) ? 0 : vendorStyle.hashCode());
        return result;
    }


    /**
     * Sets the color.
     *
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }


    /**
     * Sets the color code.
     *
     * @param colorCode the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }



    /**
     * Sets the completion date.
     *
     * @param completionDate the new completion date
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
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
     * Sets the dept id.
     *
     * @param deptId the deptId to set
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
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
     * Sets the image state.
     *
     * @param imageState the imageState to set
     */
    public void setImageState(String imageState) {
        this.imageState = imageState;
    }


    /**
     * Sets the omni channel vendor.
     *
     * @param omniChannelVendor the new omni channel vendor
     */
    public void setOmniChannelVendor(String omniChannelVendor) {
        this.omniChannelVendor = omniChannelVendor;
    }


    /**
     * Sets the omni size description.
     *
     * @param omniSizeDescription the omniSizeDescription to set
     */
    public void setOmniSizeDescriptionList(List<OmniSizeVO> omniSizeDescriptionList) {
        this.omniSizeDescriptionList = omniSizeDescriptionList;
    }


    /**
     * Sets the selected omni size code.
     * @param omniSizeCode
     */
    public void setSelectedOmniSizeCode(String selectedOmniSizeCode) {
        this.selectedOmniSizeCode = selectedOmniSizeCode;
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
     * Sets the parent style orin.
     *
     * @param parentStyleOrin the parentStyleOrin to set
     */
    public void setParentStyleOrin(String parentStyleOrin) {
        this.parentStyleOrin = parentStyleOrin;
    }


    /**
     * @param petState the petState to set
     */
    public void setPetState(String petState) {
        this.petState = petState;
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
     * Sets the vendor name.
     *
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }


    /**
     * Sets the vendor size.
     *
     * @param vendorSize the vendorSize to set
     */
    public void setVendorSize(String vendorSize) {
        this.vendorSize = vendorSize;
    }


    /**
     * Sets the vendor style.
     *
     * @param vendorStyle the vendorStyle to set
     */
    public void setVendorStyle(String vendorStyle) {
        this.vendorStyle = vendorStyle;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PetsFound [parentStyleOrin=" + parentStyleOrin
                + ", omniChannelVendor=" + omniChannelVendor + ", orinNumber="
                + orinNumber + ", deptId=" + deptId + ", productName="
                + productName + ", entryType=" + entryType + ", vendorName="
                + vendorName + ", vendorStyle=" + vendorStyle + ", imageState="
                + imageState + ", contentState=" + contentState + ", petState="
                + petState + ", completionDate=" + completionDate + ", colorCode="
                + colorCode + ", color=" + color + ", vendorSize=" + vendorSize
                + ", omniSizeDescription=" + omniSizeDescriptionList.toString() 
                + ", selectedOmniSizeCode=" + selectedOmniSizeCode + "]";
    }





}
