package com.belk.pep.model;

public class Sku {
    private String orin;
    private String orinDesc;
    private String supplierSiteId;
    private String supplierSiteName;
    private String colorCode;
    private String colorDes;
    private String sizeCode;
    private String sizeDesc;

    private String entryType;
    /**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}

	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * @return the colorDes
     */
    public String getColorDes() {
        return colorDes;
    }

    /**
     * @return the orin
     */
    public String getOrin() {
        return orin;
    }

    /**
     * @return the orinDesc
     */
    public String getOrinDesc() {
        return orinDesc;
    }

    /**
     * @return the sizeCode
     */
    public String getSizeCode() {
        return sizeCode;
    }

    /**
     * @return the sizeDesc
     */
    public String getSizeDesc() {
        return sizeDesc;
    }

    /**
     * @return the supplierSiteId
     */
    public String getSupplierSiteId() {
        return supplierSiteId;
    }

    /**
     * @return the supplierSiteName
     */
    public String getSupplierSiteName() {
        return supplierSiteName;
    }

    /**
     * @param colorCode
     *            the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * @param colorDes
     *            the colorDes to set
     */
    public void setColorDes(String colorDes) {
        this.colorDes = colorDes;
    }

    /**
     * @param orin
     *            the orin to set
     */
    public void setOrin(String orin) {
        this.orin = orin;
    }

    /**
     * @param orinDesc
     *            the orinDesc to set
     */
    public void setOrinDesc(String orinDesc) {
        this.orinDesc = orinDesc;
    }

    /**
     * @param sizeCode
     *            the sizeCode to set
     */
    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    /**
     * @param sizeDesc
     *            the sizeDesc to set
     */
    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    /**
     * @param supplierSiteId
     *            the supplierSiteId to set
     */
    public void setSupplierSiteId(String supplierSiteId) {
        this.supplierSiteId = supplierSiteId;
    }

    /**
     * @param supplierSiteName
     *            the supplierSiteName to set
     */
    public void setSupplierSiteName(String supplierSiteName) {
        this.supplierSiteName = supplierSiteName;
    }

}
