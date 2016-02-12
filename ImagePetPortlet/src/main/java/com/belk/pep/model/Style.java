package com.belk.pep.model;

import java.util.ArrayList;
import java.util.List;

public class Style {
    private String orin;
    private String parentOrin;
    private String orinDesc;
    private String supplierSiteId;
    private String supplierSiteName;
    private String colorCode;
    private String colorDes;
    private String sizeDesc;
    private List<Sku> skuList = new ArrayList<Sku>(10);

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
     * @return the parentOrin
     */
    public String getParentOrin() {
        return parentOrin;
    }

    /**
     * @return the sizeDesc
     */
    public String getSizeDesc() {
        return sizeDesc;
    }

    /**
     * @return the skuList
     */
    public List<Sku> getSkuList() {
        return skuList;
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
     * @param parentOrin
     *            the parentOrin to set
     */
    public void setParentOrin(String parentOrin) {
        this.parentOrin = parentOrin;
    }

    /**
     * @param sizeDesc
     *            the sizeDesc to set
     */
    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    /**
     * @param skuList
     *            the skuList to set
     */
    public void setSkuList(ArrayList skuObj) {
        skuList = skuObj;
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
