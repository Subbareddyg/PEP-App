
package com.belk.pep.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class HistoryVO.
 *
 * @author AFUSOS3
 */
public class HistoryVO  implements Serializable{
    

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6596998236523495138L;

    /**
     * Instantiates a new history vo.
     */
    public HistoryVO() {
       
    }
    
    /** The content creation date. */
    private  Timestamp contentCreationDate;
    
    /** The created by. */
    private String createdBy;
    
    /** The last updated by. */
    private String lastUpdatedBy;
    
    /** The content status. */
    private String contentStatus;

    /**
     * Gets the content creation date.
     *
     * @return the contentCreationDate
     */
    public Timestamp getContentCreationDate() {
        return contentCreationDate;
    }

    /**
     * Sets the content creation date.
     *
     * @param contentCreationDate the contentCreationDate to set
     */
    public void setContentCreationDate(Timestamp contentCreationDate) {
        this.contentCreationDate = contentCreationDate;
    }

    /**
     * Gets the created by.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last updated by.
     *
     * @return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the last updated by.
     *
     * @param lastUpdatedBy the lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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
        return "HistoryVO [contentCreationDate=" + contentCreationDate
            + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy
            + ", contentStatus=" + contentStatus + "]";
    }

    

}
