package com.belk.pep.jsonconverter;

public class GroupDataObject {

	
	public GroupDataObject(String groupId, String categoryId, String modifiedBy) {
        super();
        this.groupId = groupId;
        this.categoryId = categoryId;
        this.modifiedBy =modifiedBy;
    }
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	private String groupId;
	private String categoryId;
	private String modifiedBy;
}
