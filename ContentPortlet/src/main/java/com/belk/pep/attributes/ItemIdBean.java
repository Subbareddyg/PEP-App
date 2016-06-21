
package com.belk.pep.attributes;

import java.util.List;

/**
 * The Class ItemIdBean.
 *
 * @author AFUSOS3
 */
public class ItemIdBean {

  
    /** The item id. */
    private String itemId;
    
    private String groupId;
    private String groupType;
    private String modifiedBy;
    
    /** The list. */
    private List<AttributesBean> list;

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the item id.
     *
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets the list.
     *
     * @return the list
     */
    public List<AttributesBean> getList() {
        return list;
    }

    /**
     * Sets the list.
     *
     * @param list the list to set
     */
    public void setList(List<AttributesBean> list) {
        this.list = list;
    }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
    
    
}
