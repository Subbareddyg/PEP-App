/**
 * 
 */
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.List;


/**
 * The Class GroupingVO.
 *
 * @author AFUSOS3
 */
public class GroupingVO implements Serializable {

  
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1344244841967817364L;

    /**
     * Instantiates a new grouping vo.
     */
    public GroupingVO() {
        
    }

    /** The orin. */
    private String orin;
    
    /** The grouping number. */
    private String groupingNumber;
    
    /** The style number. */
    private String styleNumber;
    
    /** The style name. */
    private String styleName;    
    
    /** The brand. */
    private String brand;
    
    /** The priority. */
    private String priority;
    
    /** The default color. */
    private String defaultColor;
    
    /** The group child sku list. */
    List<GroupChildSkuVO>  groupChildSkuList;
    
    /** Grouping Type **/
    private String groupingType;
    
    /** Grouping color **/
    private String color;
    
    /** Vendor Size code description **/
    private String vendorSizeCodeDesc;
    
    /** Omni Channel Size Description **/
    private String omniChannelSizeDescription;
    
    /** Group Content Status **/
    private String contentStatus;
    
    /** Group Completion Date **/
    private String completionDate;
    
    /** Style description **/
    private String styleDescription;
    
    public String getVPN() {
		return VPN;
	}

	public void setVPN(String vPN) {
		VPN = vPN;
	}

	private String VPN;
    
    /** Style List **/
    List<StyleVO> groupChildStyleList;
    
    /** Child Group List **/
    List<GroupingVO> childGroupList;

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
     * Gets the style number.
     *
     * @return the styleNumber
     */
    public String getStyleNumber() {
        return styleNumber;
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
     * Gets the style name.
     *
     * @return the styleName
     */
    public String getStyleName() {
        return styleName;
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
     * Gets the brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand.
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the priority.
     *
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Gets the default color.
     *
     * @return the defaultColor
     */
    public String getDefaultColor() {
        return defaultColor;
    }

    /**
     * Sets the default color.
     *
     * @param defaultColor the defaultColor to set
     */
    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * Gets the group child sku list.
     *
     * @return the groupChildSkuList
     */
    public List<GroupChildSkuVO> getGroupChildSkuList() {
        return groupChildSkuList;
    }

    /**
     * Sets the group child sku list.
     *
     * @param groupChildSkuList the groupChildSkuList to set
     */
    public void setGroupChildSkuList(List<GroupChildSkuVO> groupChildSkuList) {
        this.groupChildSkuList = groupChildSkuList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GroupingVO [orin=" + orin + ", groupingNumber="
            + groupingNumber + ", styleNumber=" + styleNumber + ", styleName="
            + styleName + ", brand=" + brand + ", priority=" + priority
            + ", defaultColor=" + defaultColor + ", groupChildSkuList="
            + groupChildSkuList + "]";
    }

	public String getGroupingType() {
		return groupingType;
	}

	public void setGroupingType(String groupingType) {
		this.groupingType = groupingType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getVendorSizeCodeDesc() {
		return vendorSizeCodeDesc;
	}

	public void setVendorSizeCodeDesc(String vendorSizeCodeDesc) {
		this.vendorSizeCodeDesc = vendorSizeCodeDesc;
	}

	public String getOmniChannelSizeDescription() {
		return omniChannelSizeDescription;
	}

	public void setOmniChannelSizeDescription(String omniChannelSizeDescription) {
		this.omniChannelSizeDescription = omniChannelSizeDescription;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public List<StyleVO> getGroupChildStyleList() {
		return groupChildStyleList;
	}

	public void setGroupChildStyleList(List<StyleVO> groupChildStyleList) {
		this.groupChildStyleList = groupChildStyleList;
	}

	public List<GroupingVO> getChildGroupList() {
		return childGroupList;
	}

	public void setChildGroupList(List<GroupingVO> childGroupList) {
		this.childGroupList = childGroupList;
	}

	public String getStyleDescription() {
		return styleDescription;
	}

	public void setStyleDescription(String styleDescription) {
		this.styleDescription = styleDescription;
	}
    
    
}
