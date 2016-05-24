package com.belk.pep.form;

import java.util.List;




/**
 * This class is used to save the Group Header data as a Form data
 * @author AFUPYB3
 *
 */
public class CreateGroupForm {
    private static final long serialVersionUID = 1L;
    
//    private List <String> pageNumberList;
    
    
    private String groupId;
    private String groupName;
    private String groupType;
    private String groupDesc;
    private String groupLaunchDate;
    private String endDate;

    /** User role**/
    private String roleEditable;
    private String readOnlyUser="no";
    private String roleName;
    
    private String groupCretionMsg;
    private String groupCreationStatus;
    
    private List <GroupAttributeForm> groupAttributeFormList;
    
    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }
    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
     * @return the groupType
     */
    public String getGroupType() {
        return groupType;
    }
    /**
     * @param groupType the groupType to set
     */
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
    /**
     * @return the groupDesc
     */
    public String getGroupDesc() {
        return groupDesc;
    }
    /**
     * @param groupDesc the groupDesc to set
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
    /**
     * @return the groupLaunchDate
     */
    public String getGroupLaunchDate() {
        return groupLaunchDate;
    }
    /**
     * @param groupLaunchDate the groupLaunchDate to set
     */
    public void setGroupLaunchDate(String groupLaunchDate) {
        this.groupLaunchDate = groupLaunchDate;
    }
    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the roleEditable
     */
    public String getRoleEditable() {
        return roleEditable;
    }
    /**
     * @param roleEditable the roleEditable to set
     */
    public void setRoleEditable(String roleEditable) {
        this.roleEditable = roleEditable;
    }
    /**
     * @return the readOnlyUser
     */
    public String getReadOnlyUser() {
        return readOnlyUser;
    }
    /**
     * @param readOnlyUser the readOnlyUser to set
     */
    public void setReadOnlyUser(String readOnlyUser) {
        this.readOnlyUser = readOnlyUser;
    }
    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    /**
     * @return the groupCrationMsg
     */
    public String getGroupCretionMsg() {
        return groupCretionMsg;
    }
    /**
     * @param groupCrationMsg the groupCrationMsg to set
     */
    public void setGroupCretionMsg(String groupCretionMsg) {
        this.groupCretionMsg = groupCretionMsg;
    }
    /**
     * @return the groupAttributeFormList
     */
    public List<GroupAttributeForm> getGroupAttributeFormList() {
        return groupAttributeFormList;
    }
    /**
     * @param groupAttributeFormList the groupAttributeFormList to set
     */
    public void setGroupAttributeFormList(
        List<GroupAttributeForm> groupAttributeFormList) {
        this.groupAttributeFormList = groupAttributeFormList;
    }
    /**
     * @return the groupCreationStatus
     */
    public String getGroupCreationStatus() {
        return groupCreationStatus;
    }
    /**
     * @param groupCreationStatus the groupCreationStatus to set
     */
    public void setGroupCreationStatus(String groupCreationStatus) {
        this.groupCreationStatus = groupCreationStatus;
    }
    
    
  }
