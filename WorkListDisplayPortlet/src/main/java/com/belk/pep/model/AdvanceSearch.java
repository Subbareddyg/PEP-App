package com.belk.pep.model;

import java.util.List;

import com.belk.pep.util.ClassDetails;
/**
 * This Class is responsible to hold the Advance search Object values
 * @author AFUBXJ1
 *
 */
public class AdvanceSearch {
    
    private List<ImageStatusDropValues> imageStatusDropDown;
    
    private List<ContentStatusDropValues> contentStatusDropDown;
    
    private List<RequestTypeDropValues> requestTypeDropDown;
    
    private List <ClassDetails> classDetails;
    
    private String imageStatus;
    
    private String contentStatus;
    
    private String requestType;
    
    private String active;
    private String inActive;
    private String closed;
    
    private String createdToday;
    
    private String deptNumbers;
    
    private String dateFrom;
    
    private String dateTo;
    
    private String orin;
    
    private String vendorStyle;
    
    private String upc;
    
    private String classNumber;
    
    private boolean isAllFieldEmpty;
    
    //Added by Avik
    private String vendorNumber;
    

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }
    //End
    
    public boolean isAllFieldEmpty() {
        return isAllFieldEmpty;
    }

    public void setAllFieldEmpty(boolean isAllFieldEmpty) {
        this.isAllFieldEmpty = isAllFieldEmpty;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

   

    public String getVendorStyle() {
        return vendorStyle;
    }

    public void setVendorStyle(String vendorStyle) {
        this.vendorStyle = vendorStyle;
    }

    public String getOrin() {
        return orin;
    }

    public void setOrin(String orin) {
        this.orin = orin;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDeptNumbers() {
        return deptNumbers;
    }

    public void setDeptNumbers(String deptNumbers) {
        this.deptNumbers = deptNumbers;
    }

    public List<ImageStatusDropValues> getImageStatusDropDown() {
        return imageStatusDropDown;
    }

    public void setImageStatusDropDown(
        List<ImageStatusDropValues> imageStatusDropDown) {
        this.imageStatusDropDown = imageStatusDropDown;
    }

   
    

    public List<ContentStatusDropValues> getContentStatusDropDown() {
        return contentStatusDropDown;
    }

    public void setContentStatusDropDown(
        List<ContentStatusDropValues> contentStatusDropDown) {
        this.contentStatusDropDown = contentStatusDropDown;
    }

    public List<RequestTypeDropValues> getRequestTypeDropDown() {
        return requestTypeDropDown;
    }

    public void setRequestTypeDropDown(
        List<RequestTypeDropValues> requestTypeDropDown) {
        this.requestTypeDropDown = requestTypeDropDown;
    }

   

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    
    public String getInActive() {
        return inActive;
    }

    public void setInActive(String inActive) {
        this.inActive = inActive;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getCreatedToday() {
        return createdToday;
    }

    public void setCreatedToday(String createdToday) {
        this.createdToday = createdToday;
    }

    public List<ClassDetails> getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(List<ClassDetails> classDetails) {
        this.classDetails = classDetails;
    }
    
    

}
