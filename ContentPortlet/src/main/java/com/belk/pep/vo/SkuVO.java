
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.List;



/**
 * The Class SkuVO.
 */
public class SkuVO  implements Serializable{


    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4742910274704461468L;

    /** The style id. */
    private String styleId;


    /** The orin. */
    private String orin;

    /** The color code. */
    private String colorCode;

    /** The color. */
    private String color;

    /** The content status. */
    private String contentStatus;

    /** The completion date. */
    private  String completionDate;

    /** The vendor size. */
    private String vendorSize;


    /** The omni channel size description. */
    private List<OmniSizeVO> omniChannelSizeDescriptionList;
    
    /** The selected omni channel size code. */
    private String selectedOmniChannelSizeCode;

    /** The entry type. */
    private String entryType;

    private String contentStatusCode;

    private String parentStyleColor;
    
    private String VPN;
    
    public String getVPN() {
		return VPN;
	}


	public void setVPN(String vPN) {
		VPN = vPN;
	}


	public String getParentStyleColor() {
		return parentStyleColor;
	}


	public void setParentStyleColor(String parentStyleColor) {
		this.parentStyleColor = parentStyleColor;
	}


	/**
     * Instantiates a new sku vo.
     */
    public SkuVO() {

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
     * Gets the color code.
     *
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
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
     * Gets the omni channel size description.
     *
     * @return the omniChannelSizeDescription
     */
    public List<OmniSizeVO> getOmniChannelSizeDescriptionList() {
        return omniChannelSizeDescriptionList;
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
     * Gets the style id.
     *
     * @return the styleId
     */
    public String getStyleId() {
        return styleId;
    }

    /**
     * Gets the vendor size.
     *
     * @return the vendorSize
     */
    public String getVendorSize() {
        return vendorSize;
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
     * Sets the color code.
     *
     * @param colorCode the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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
     * Sets the omni channel size description.
     *
     * @param omniChannelSizeDescription the omniChannelSizeDescription to set
     */
    public void setOmniChannelSizeDescriptionList(List<OmniSizeVO> omniChannelSizeDescriptionList) {
        this.omniChannelSizeDescriptionList = omniChannelSizeDescriptionList;
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
     * Sets the style id.
     *
     * @param styleId the styleId to set
     */
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    /**
     * Sets the vendor size.
     *
     * @param vendorSize the vendorSize to set
     */
    public void setVendorSize(String vendorSize) {
        this.vendorSize = vendorSize;
    }
    
    /**
     * Get the selected omni channel size description.
     * @return
     */
    public String getSelectedOmniChannelSizeCode() {
        return selectedOmniChannelSizeCode;
    }


    /**
     * Set the selected omni channel size description.
     * @param selectedOmniChannelSizeDescription
     */
    public void setSelectedOmniChannelSizeCode(String selectedOmniChannelSizeCode) {
        this.selectedOmniChannelSizeCode = selectedOmniChannelSizeCode;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SkuVO [styleId=" + styleId + ", orin=" + orin + ", colorCode="
                + colorCode + ", color=" + color + ", contentStatus="
                + contentStatus + ", completionDate=" + completionDate
                + ", vendorSize=" + vendorSize + ", omniChannelSizeDescription="
                + omniChannelSizeDescriptionList.toString() + ", entryType=" + entryType 
                + ", selectedOmniChannelSizeCode=" + selectedOmniChannelSizeCode + "]";
    }




}
