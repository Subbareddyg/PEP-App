
package com.belk.pep.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentHistoryVO.
 *
 * @author AFUSOS3
 */
public class ContentHistoryVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new content history vo.
     */
    public ContentHistoryVO() {
       
    }

    /** The orin number. */
    private String orinNumber;
    
    /** The content created date. */
    private String contentCreatedDate;
    
    /** The content created by. */
    private String contentCreatedBy;
    
    /** The content last updated by. */
    private String contentLastUpdatedBy;
    
    /** The content last status. */
    private String contentStatus;
    
    /** The entry type. */
    private String entryType;

    /** The content created date. */
    private String contentLastUpdatedDate;

    /**
     * Gets the content last updated date.
     *
     * @return the content last updated date
     */
    public String getContentLastUpdatedDate() {
        return contentLastUpdatedDate;
    }

    /**
     * Sets the content last updated date.
     *
     * @param contentLastUpdatedDate the new content last updated date
     */
    public void setContentLastUpdatedDate(String contentLastUpdatedDate) {
        this.contentLastUpdatedDate = contentLastUpdatedDate;
    }

    /**
     * Gets the orin number.
     *
     * @return the orinNumber
     */
    public String getOrinNumber() {
        return orinNumber;
    }

    /**
     * Sets the orin number.
     *
     * @param orinNumber the orinNumber to set
     */
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }

    /**
     * Gets the content created date.
     *
     * @return the contentCreatedDate
     */
    public String getContentCreatedDate() {
        return contentCreatedDate;
    }

    /**
     * Sets the content created date.
     *
     * @param contentCreatedDate the contentCreatedDate to set
     */
    public void setContentCreatedDate(String contentCreatedDate) {
        this.contentCreatedDate = contentCreatedDate;
    }

    /**
     * Gets the content created by.
     *
     * @return the contentCreatedBy
     */
    public String getContentCreatedBy() {
        return contentCreatedBy;
    }

    /**
     * Sets the content created by.
     *
     * @param contentCreatedBy the contentCreatedBy to set
     */
    public void setContentCreatedBy(String contentCreatedBy) {
        this.contentCreatedBy = contentCreatedBy;
    }

    /**
     * Gets the content last updated by.
     *
     * @return the contentLastUpdatedBy
     */
    public String getContentLastUpdatedBy() {
        return contentLastUpdatedBy;
    }

    /**
     * Sets the content last updated by.
     *
     * @param contentLastUpdatedBy the contentLastUpdatedBy to set
     */
    public void setContentLastUpdatedBy(String contentLastUpdatedBy) {
        this.contentLastUpdatedBy = contentLastUpdatedBy;
    }

   
    /**
     * Gets the entry type.
     *
     * @return the entryType
     */
    public String getEntryType() {
        return entryType;
    }

    /**
     * Sets the entry type.
     *
     * @param entryType the entryType to set
     */
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    /**
     * Gets the content status.
     *
     * @return the contentStatus
     */
    public String getContentStatus() {
        return contentStatus;
    }

    /**
     * Sets the content status.
     *
     * @param contentStatus the contentStatus to set
     */
    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContentHistoryVO [orinNumber=" + orinNumber
            + ", contentCreatedDate=" + contentCreatedDate
            + ", contentCreatedBy=" + contentCreatedBy
            + ", contentLastUpdatedBy=" + contentLastUpdatedBy
            + ", contentStatus=" + contentStatus + ", entryType=" + entryType
            + "]";
    }
    
    
    
}
