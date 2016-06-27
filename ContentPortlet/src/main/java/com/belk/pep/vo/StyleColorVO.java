package com.belk.pep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * The Class StyleColorVO.
 */
public class StyleColorVO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -412877389533986694L;

    /** The entry type. */
    private String  entryType;

    /** The parent style orin number. */
    private String parentStyleOrinNumber;

    /** The orin number. */
    private String orinNumber;

    /** The vendor size. */
    private String vendorSize;

    /** The omni size description. */
    private String omniSizeDescription;

    /** The content status code. */
    private String contentStatusCode;

    /** The orin. */
    private String orin;

    /** The grouping number. */
    private String groupingNumber;

    /** The style number. */
    private String styleNumber;

    /** The style name. */
    private String styleName;

    /** The color. */
    private String color;

    private String VPN;



    public String getVPN() {
		return VPN;
	}

	public void setVPN(String vPN) {
		VPN = vPN;
	}

	/** The content status. */
    private String contentStatus;

    /** The completion date. */
    private  String completionDate;

    /** The action. */
    private String action;

    /** The sku list. */
    List<SkuVO>  skuList;

    /**
     * Instantiates a new style color vo.
     */
    public StyleColorVO() {

    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
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
     * Gets the completion date.
     *
     * @return the completionDate
     */
    public String getCompletionDate() {
        return completionDate;
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
     * @return the contentStatusCode
     */
    public String getContentStatusCode() {
        return contentStatusCode;
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
     * Gets the grouping number.
     *
     * @return the groupingNumber
     */
    public String getGroupingNumber() {
        return groupingNumber;
    }

    /**
     * Gets the omni size description.
     *
     * @return the omniSizeDescription
     */
    public String getOmniSizeDescription() {
        return omniSizeDescription;
    }

    /**
     * Gets the orin.
     *
     * @return the orin
     */
    public String getOrin() {
        return orin;
    }

    /**
     * @return the orinNumber
     */
    public String getOrinNumber() {
        return orinNumber;
    }

    /**
     * @return the parentStyleOrinNumber
     */
    public String getParentStyleOrinNumber() {
        return parentStyleOrinNumber;
    }


    /**
     * Gets the sku list.
     *
     * @return the skuList
     */
    public List<SkuVO> getSkuList() {
        if(skuList==null)
        {
            skuList= new ArrayList<SkuVO>();
        }
        return skuList;
    }

    /**
     * Gets the style name.
     *
     * @return the styleName
     */
    public String getStyleName() {
        return styleName;
    }

    /**
     * Gets the style number.
     *
     * @return the styleNumber
     */
    public String getStyleNumber() {
        return styleNumber;
    }

    /**
     * @return the vendorSize
     */
    public String getVendorSize() {
        return vendorSize;
    }

    /**
     * Sets the action.
     *
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
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
     * Sets the completion date.
     *
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Sets the content status.
     *
     * @param contentStatus the contentStatus to set
     */
    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    /**
     * @param contentStatusCode the contentStatusCode to set
     */
    public void setContentStatusCode(String contentStatusCode) {
        this.contentStatusCode = contentStatusCode;
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
     * Sets the grouping number.
     *
     * @param groupingNumber the groupingNumber to set
     */
    public void setGroupingNumber(String groupingNumber) {
        this.groupingNumber = groupingNumber;
    }

    /**
     * Sets the omni size description.
     *
     * @param omniSizeDescription the omniSizeDescription to set
     */
    public void setOmniSizeDescription(String omniSizeDescription) {
        this.omniSizeDescription = omniSizeDescription;
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
     * @param orinNumber the orinNumber to set
     */
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }

    /**
     * @param parentStyleOrinNumber the parentStyleOrinNumber to set
     */
    public void setParentStyleOrinNumber(String parentStyleOrinNumber) {
        this.parentStyleOrinNumber = parentStyleOrinNumber;
    }




    /**
     * Sets the sku list.
     *
     * @param skuList the skuList to set
     */
    public void setSkuList(List<SkuVO> skuList) {
        this.skuList = skuList;
    }

    /**
     * Sets the style name.
     *
     * @param styleName the styleName to set
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    /**
     * Sets the style number.
     *
     * @param styleNumber the styleNumber to set
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }

    /**
     * @param vendorSize the vendorSize to set
     */
    public void setVendorSize(String vendorSize) {
        this.vendorSize = vendorSize;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StyleColorVO [entryType=" + entryType
                + ", parentStyleOrinNumber=" + parentStyleOrinNumber
                + ", orinNumber=" + orinNumber + ", vendorSize=" + vendorSize
                + ", omniSizeDescription=" + omniSizeDescription
                + ", contentStatusCode=" + contentStatusCode + ", orin=" + orin
                + ", groupingNumber=" + groupingNumber + ", styleNumber="
                + styleNumber + ", styleName=" + styleName + ", color=" + color
                + ", contentStatus=" + contentStatus + ", completionDate="
                + completionDate + ", action=" + action + ", skuList=" + skuList
                + "]";
    }






}
