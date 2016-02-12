package com.belk.pep.model;

import java.util.ArrayList;
import java.util.List;

public class WorkFlow {
    private String orin;

    private String orinDesc;

    private String supplierSiteName;
    private String colorDes;
    
    private String entryType;   
    
    
    
    public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

    private List<Style> styleList = new ArrayList<Style>(10);
    
    private Sku skuObj ;
    public Sku getSkuObj() {
		return skuObj;
	}

	public void setSkuObj(Sku skuObj) {
		this.skuObj = skuObj;
	}

	private Style styleObj;

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

    public List<Style> getStyleList() {

        return styleList;
    }

    public Style getStyleObj() {
        return styleObj;
    }

    /**
     * @return the supplierSiteName
     */
    public String getSupplierSiteName() {
        return supplierSiteName;
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

    public void setStyleList(ArrayList styleObj) {
        styleList = styleObj;
        // this.styleList = styleList;
    }

    /**
     * @param supplierSiteName
     *            the supplierSiteName to set
     */
    public void setSupplierSiteName(String supplierSiteName) {
        this.supplierSiteName = supplierSiteName;
    }

}
