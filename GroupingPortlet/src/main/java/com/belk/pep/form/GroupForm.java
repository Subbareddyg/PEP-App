package com.belk.pep.form;

import java.util.List;

/**
 * Class to hold the Group details
 * 
 * Class added For PIM Phase 2 - Group Search Date: 05/19/2016 Added By:
 * Cognizant
 */
public class GroupForm {

	private String groupId;
	private String groupName;
	private String groupType;
	private String groupTypeCode;
	private String StartDate;
	private String endDate;
	private String groupContentStatus;
	private String groupImageStatus;
	private String childGroup;
	private List<GroupForm> childList;

	/**
	 * @return the groupTypeCode
	 */
	public String getGroupTypeCode() {
		return groupTypeCode;
	}

	/**
	 * @param groupTypeCode
	 *            the groupTypeCode to set
	 */
	public void setGroupTypeCode(String groupTypeCode) {
		this.groupTypeCode = groupTypeCode;
	}

	/**
	 * @return the groupContentStatus
	 */
	public String getGroupContentStatus() {
		return groupContentStatus;
	}

	/**
	 * @param groupContentStatus
	 *            the groupContentStatus to set
	 */
	public void setGroupContentStatus(String groupContentStatus) {
		this.groupContentStatus = groupContentStatus;
	}

	/**
	 * @return the groupImageStatus
	 */
	public String getGroupImageStatus() {
		return groupImageStatus;
	}

	/**
	 * @param groupImageStatus
	 *            the groupImageStatus to set
	 */
	public void setGroupImageStatus(String groupImageStatus) {
		this.groupImageStatus = groupImageStatus;
	}

	/**
	 * @return the childList
	 */
	public List<GroupForm> getChildList() {
		return childList;
	}

	/**
	 * @param childList
	 *            the childList to set
	 */
	public void setChildList(List<GroupForm> childList) {
		this.childList = childList;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return StartDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
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
	 * @param groupName
	 *            the groupName to set
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
	 * @param groupType
	 *            the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the childGroup
	 */
	public String getChildGroup() {
		return childGroup;
	}

	/**
	 * @param childGroup
	 *            the childGroup to set
	 */
	public void setChildGroup(String childGroup) {
		this.childGroup = childGroup;
	}

}
