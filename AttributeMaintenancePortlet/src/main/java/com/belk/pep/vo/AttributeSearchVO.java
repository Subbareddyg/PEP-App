package com.belk.pep.vo;

import java.io.Serializable;

/**
 * The Class AttributeSearchVO.
 */
public class AttributeSearchVO implements Serializable {

    /**
     * Instantiates a new attribute search vo.
     */
    public AttributeSearchVO() {

    }
 
    /** The category iph. */
    private String categoryIPH;

    /** The category iph. */
    private String categoryIdAndName;
    
    /** The attribute type pim. */
    private String attributeTypePIM;
    
    /** The attribute type bm. */
    private String attributeTypeBM;
    
    /** The attribute status. */
    private String attributeStatus;
    
    
    /**
     * Gets the category iph.
     *
     * @return the category iph
     */
    public String getCategoryIPH() {
        return categoryIPH;
    }

    /**
     * Sets the category iph.
     *
     * @param categoryIPH the new category iph
     */
    public void setCategoryIPH(String categoryIPH) {
        this.categoryIPH = categoryIPH;
    }

    /**
     * Gets the attribute status.
     *
     * @return the attribute status
     */
    public String getAttributeStatus() {
        return attributeStatus;
    }

    /**
     * Sets the attribute status.
     *
     * @param attributeStatus the new attribute status
     */
    public void setAttributeStatus(String attributeStatus) {
        this.attributeStatus = attributeStatus;
    }

    /**
     * Gets the attribute type pim.
     *
     * @return the attribute type pim
     */
    public String getAttributeTypePIM() {
        return attributeTypePIM;
    }

    /**
     * Sets the attribute type pim.
     *
     * @param attributeTypePIM the new attribute type pim
     */
    public void setAttributeTypePIM(String attributeTypePIM) {
        this.attributeTypePIM = attributeTypePIM;
    }

    /**
     * Gets the attribute type bm.
     *
     * @return the attribute type bm
     */
    public String getAttributeTypeBM() {
        return attributeTypeBM;
    }

    /**
     * Sets the attribute type bm.
     *
     * @param attributeTypeBM the new attribute type bm
     */
    public void setAttributeTypeBM(String attributeTypeBM) {
        this.attributeTypeBM = attributeTypeBM;
    }

    /**
     * Gets the category id and name.
     *
     * @return the category id and name
     */
    public String getCategoryIdAndName() {
        return categoryIdAndName;
    }

    /**
     * Sets the category id and name.
     *
     * @param categoryIdAndName the new category id and name
     */
    public void setCategoryIdAndName(String categoryIdAndName) {
        this.categoryIdAndName = categoryIdAndName;
    }
    
    
    
}
