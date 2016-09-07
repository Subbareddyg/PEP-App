package com.belk.pep.common.model;

import java.io.Serializable;

/**
 * Created by afumyr7 on 8/24/2016.
 */
public class PageAnchorDetails implements Serializable {

    /**
     * The Constant serialVersionUID.
     */

    private static final long serialVersionUID = 1L;

    private String pageNumber;
    private String orinNumber;
    private String groupId;

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getOrinNumber() {
        return orinNumber;
    }

    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
