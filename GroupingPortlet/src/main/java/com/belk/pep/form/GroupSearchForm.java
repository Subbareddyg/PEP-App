package com.belk.pep.form;

import java.util.List;

/**
 * Class to hold the Search group search criteria
 * 
 * Class added For PIM Phase 2 - Group Search 
 * Date: 05/18/2016
 * Added By: Cognizant
 */
public class GroupSearchForm {
    
    private String GroupId;
    private String groupName;
    private String vendor;
    private String depts;
    private String classes;
    private String orinNumber;
    private String supplierSiteId;
    private int totalRecordCount = 0;
    private int recordsPerPage = 0;
    private int pageNumber = 0;
    private String sortColumn;
    private String ascDescOrder;
    
    /**
     * @return the totalRecordCount
     */
    public int getTotalRecordCount() {
        return totalRecordCount;
    }
    /**
     * @param totalRecordCount the totalRecordCount to set
     */
    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }
    /**
     * @return the recordsPerPage
     */
    public int getRecordsPerPage() {
        return recordsPerPage;
    }
    /**
     * @param recordsPerPage the recordsPerPage to set
     */
    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }
    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }
    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    /**
     * @return the sortColumn
     */
    public String getSortColumn() {
        return sortColumn;
    }
    /**
     * @param sortColumn the sortColumn to set
     */
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }
    /**
     * @return the ascDescOrder
     */
    public String getAscDescOrder() {
        return ascDescOrder;
    }
    /**
     * @param ascDescOrder the ascDescOrder to set
     */
    public void setAscDescOrder(String ascDescOrder) {
        this.ascDescOrder = ascDescOrder;
    }
    private List<GroupForm> groupList;
    
    /**
     * @return the groupList
     */
    public List<GroupForm> getGroupList() {
        return groupList;
    }
    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(List<GroupForm> groupList) {
        this.groupList = groupList;
    }
    /**
     * @return the groupId
     */
    public String getGroupId() {
        return GroupId;
    }
    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }
    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    /**
     * @return the depts
     */
    public String getDepts() {
        return depts;
    }
    /**
     * @param depts the depts to set
     */
    public void setDepts(String depts) {
        this.depts = depts;
    }
    /**
     * @return the classes
     */
    public String getClasses() {
        return classes;
    }
    /**
     * @param classes the classes to set
     */
    public void setClasses(String classes) {
        this.classes = classes;
    }
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
     * @return the supplierSiteId
     */
    public String getSupplierSiteId() {
        return supplierSiteId;
    }
    /**
     * @param supplierSiteId the supplierSiteId to set
     */
    public void setSupplierSiteId(String supplierSiteId) {
        this.supplierSiteId = supplierSiteId;
    }

}
