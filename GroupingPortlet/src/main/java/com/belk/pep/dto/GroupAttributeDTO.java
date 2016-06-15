package com.belk.pep.dto;




/**
 * This class is used to save the Group Header data as a Form data.
 * @author AFUPYB3
 *
 */
public class GroupAttributeDTO {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
    
    // Orin Number
    private String orinNumber;
    private String styleNumber;
    //XML SHredding
    private String prodName;
    //Color Code
    private String colorCode;
    private String colorName;
    private String size;
    // Already In Group
    private String isAlreadyInGroup;
    // Is default
    private String isDefault;
    /**
     * @return the orinNumber
     */
    public String getOrinNumber() {
        return orinNumber;
    }
    /**
     * @param orinNumber the orinNumber to set
     */
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }
    /**
     * @return the styleNumber
     */
    public String getStyleNumber() {
        return styleNumber;
    }
    /**
     * @param styleNumber the styleNumber to set
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }
    /**
     * @return the prodName
     */
    public String getProdName() {
        return prodName;
    }
    /**
     * @param prodName the prodName to set
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
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
     * @return the size
     */
    public String getSize() {
        return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }
    /**
     * @return the isAlreadyInGroup
     */
    public String getIsAlreadyInGroup() {
        return isAlreadyInGroup;
    }
    /**
     * @param isAlreadyInGroup the isAlreadyInGroup to set
     */
    public void setIsAlreadyInGroup(String isAlreadyInGroup) {
        this.isAlreadyInGroup = isAlreadyInGroup;
    }
    /**
     * @return the isDefault
     */
    public String getIsDefault() {
        return isDefault;
    }
    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    
  }
