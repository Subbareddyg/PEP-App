/**
 * 
 */
package com.belk.pep.vo;

import java.io.Serializable;

/**
 * The Class ChildSkuVO.
 *
 * @author AFUSOS3
 */
public class ChildSkuVO  implements Serializable{

    /**
     * Instantiates a new child sku vo.
     */
    public ChildSkuVO() {
        
    }
    
    /** The orin number. */
    private String   orinNumber;
    
    /** The vendor name. */
    private String   vendorName;
    
    /** The style name. */
    private String   styleName;
    
    /** The style number. */
    private String styleNumber;
    
    /** The color code. */
    private String colorCode;
    
    /** The color name. */
    private String colorName;
    
    /** The size name. */
    private String sizeName;
    
    /** The entry type. */
    private String entryType;

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
     * Gets the style name.
     *
     * @return the styleName
     */
    public String getStyleName() {
        return styleName;
    }

    /**
     * Sets the style name.
     *
     * @param styleName the styleName to set
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    /**
     * Gets the style number.
     *
     * @return the styleNumber
     */
    public String getStyleNumber() {
        return styleNumber;
    }

    /**
     * Sets the style number.
     *
     * @param styleNumber the styleNumber to set
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }

    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * @param colorCode the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * @return the colorName
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * @param colorName the colorName to set
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * Gets the size name.
     *
     * @return the sizeName
     */
    public String getSizeName() {
        return sizeName;
    }

    /**
     * Sets the size name.
     *
     * @param sizeName the sizeName to set
     */
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ChildSkuVO [orinNumber=" + orinNumber + ", vendorName="
            + vendorName + ", styleName=" + styleName + ", styleNumber="
            + styleNumber + ", colorCode=" + colorCode + ", colorName="
            + colorName + ", sizeName=" + sizeName + ", entryType=" + entryType
            + "]";
    }
    
    
    
    

}
