
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
    
    
}
