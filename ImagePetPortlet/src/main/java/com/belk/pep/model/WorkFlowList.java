package com.belk.pep.model;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowList {
    
    private String orin;
    private String orinDesc;
    private String supplierSiteName;
    private String colorDes;
    private String workflowImageStatus;
    private String workflowCompletionDate;
    private Style styleObj;
    private String styleInfo;
    private List<Style> styleList = new ArrayList<Style>(10);
    
    public String getWorkflowImageStatus() {
        return workflowImageStatus;
    }

    public String getStyleInfo() {
        return styleInfo;
    }

    public void setStyleInfo(String styleInfo) {
        this.styleInfo = styleInfo;
    }

    public void setWorkflowImageStatus(String workflowImageStatus) {
        this.workflowImageStatus = workflowImageStatus;
    }

    public String getWorkflowCompletionDate() {
        return workflowCompletionDate;
    }

    public void setWorkflowCompletionDate(String workflowCompletionDate) {
        this.workflowCompletionDate = workflowCompletionDate;
    }

    public void setStyleList(List<Style> styleList) {
        this.styleList = styleList;
    }

    public void setStyleObj(Style styleObj) {
        this.styleObj = styleObj;
    }

    

    
    

    /**
     * @return the colorDes
     */
    public String getColorDes() {
        return colorDes;
    }

    /**
     * @return the orin;
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
