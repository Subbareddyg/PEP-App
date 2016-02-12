
package com.belk.pep.vo;

import java.io.Serializable;

/**
 * The Class CopyAttributesVO.
 *
 * @author AFUSOS3
 */
public class CopyAttributesVO implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2519064221549119642L;

    /**
     * Instantiates a new copy attributes vo.
     */
    public CopyAttributesVO() {
        
    }

    /** The orin. */
    private String orin;
    
    /** The style id. */
    private String styleId;
    
    /** The color. */
    private String color;
    
    /** The size. */
    private String size;
    
    /** The entry type. */
    private String entryType;
    
    
    
    /** The grouping number. */
    private String groupingNumber;
    
  

    /**
     * Gets the orin.
     *
     * @return the orin
     */
    public String getOrin() {
        return orin;
    }

    /**
     * Sets the orin.
     *
     * @param orin the orin to set
     */
    public void setOrin(String orin) {
        this.orin = orin;
    }

    /**
     * Gets the grouping number.
     *
     * @return the groupingNumber
     */
    public String getGroupingNumber() {
        return groupingNumber;
    }

    /**
     * Sets the grouping number.
     *
     * @param groupingNumber the groupingNumber to set
     */
    public void setGroupingNumber(String groupingNumber) {
        this.groupingNumber = groupingNumber;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the styleId
     */
    public String getStyleId() {
        return styleId;
    }

    /**
     * Sets the style id.
     *
     * @param styleId the styleId to set
     */
    public void setStyleId(String styleId) {
        this.styleId = styleId;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CopyAttributesVO [orin=" + orin + ", styleId=" + styleId
            + ", color=" + color + ", size=" + size + ", entryType="
            + entryType + ", groupingNumber=" + groupingNumber + "]";
    }
    
    
    
    
    
    
}
