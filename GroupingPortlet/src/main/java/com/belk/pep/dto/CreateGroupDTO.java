package com.belk.pep.dto;

import java.util.List;

import com.belk.pep.form.GroupAttributeForm;




/**
 * This class is used to save the Group Header data as a Form data.
 * @author AFUPYB3
 *
 */
public class CreateGroupDTO {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
    

    
    
    private String groupId;
    private String groupName;
    private String groupType;
    private String groupDesc;
    private String groupLaunchDate;
    private String endDate;
    private String groupStatus;
    private String carsGroupType;

    /** User role.**/
    private String roleEditable;
    private String readOnlyUser="no";
    private String roleName;
    
    private String groupCretionMsg;
    
    private List <GroupAttributeForm> groupAttributeDTOList;
    
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
     * @return the groupAttributeDTOList
     */
    public List<GroupAttributeForm> getGroupAttributeDTOList() {
        return groupAttributeDTOList;
    }
    /**
     * @param groupAttributeDTOList the groupAttributeDTOList to set
     */
    public void setGroupAttributeDTOList(
        List<GroupAttributeForm> groupAttributeDTOList) {
        this.groupAttributeDTOList = groupAttributeDTOList;
    }
	/**
	 * @return the groupStatus
	 */
	public String getGroupStatus() {
		return groupStatus;
	}
	/**
	 * @param groupStatus the groupStatus to set
	 */
	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	/**
	 * @return the carsGroupType
	 */
	public String getCarsGroupType() {
		return carsGroupType;
	}
	/**
	 * @param carsGroupType the carsGroupType to set
	 */
	public void setCarsGroupType(String carsGroupType) {
		this.carsGroupType = carsGroupType;
	}
    
    
    
  }
