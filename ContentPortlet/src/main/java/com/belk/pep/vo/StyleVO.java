
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * The Class StyleVO.
 */
public class StyleVO  implements Serializable{



    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8026107334244348591L;

    /** The orin. */
    private String orin;

    /** The grouping number. */
    private String groupingNumber;

    /** The style number. */
    private String styleNumber;

    /** The style name. */
    private String styleName;

    /** The content status. */
    private String contentStatus;

    /** The content status. */
    private String contentStatusCode;

    /** The color. */
    private String color;



    /** The action. */
    private String action;

    /** The style color list. */
    List<StyleColorVO> styleColorList;

    /** The entry type. */
    private String entryType;

    /** The orin number. */
    private String orinNumber;

    /** The parent orin number. */
    private String  parentOrinNumber;

    /** The vendor size. */
    private String vendorSize;

    /** The omni size description. */
    private String omniSizeDescription;


    /** The content state. */
    private String contentState;

    /** The completion date. */
    private String completionDate ;

    /**
     * Instantiates a new style vo.
     */
    public StyleVO() {

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
     * Gets the content state.
     *
     * @return the contentState
     */
    public String getContentState() {
        return contentState;
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
     * Gets the content status code.
     *
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
     * @return the parentOrinNumber
     */
    public String getParentOrinNumber() {
        return parentOrinNumber;
    }

    /**
     * Gets the style color list.
     *
     * @return the styleColorList
     */
    public List<StyleColorVO> getStyleColorList() {
        if(styleColorList==null)
        {
            styleColorList= new ArrayList<StyleColorVO>();
        }
        return styleColorList;

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
     * Gets the vendor size.
     *
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
     * Sets the content state.
     *
     * @param contentState the contentState to set
     */
    public void setContentState(String contentState) {
        this.contentState = contentState;
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
     * Sets the content status code.
     *
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
     * @param parentOrinNumber the parentOrinNumber to set
     */
    public void setParentOrinNumber(String parentOrinNumber) {
        this.parentOrinNumber = parentOrinNumber;
    }

    /**
     * Sets the style color list.
     *
     * @param styleColorList the styleColorList to set
     */
    public void setStyleColorList(List<StyleColorVO> styleColorList) {

        this.styleColorList = styleColorList;
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
     * Sets the vendor size.
     *
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
        return "StyleVO [orin=" + orin + ", groupingNumber=" + groupingNumber
                + ", styleNumber=" + styleNumber + ", styleName=" + styleName
                + ", contentStatus=" + contentStatus + ", action=" + action
                + ", styleColorList=" + styleColorList + ", entryType=" + entryType
                + ", orinNumber=" + orinNumber + ", parentOrinNumber="
                + parentOrinNumber + ", vendorSize=" + vendorSize
                + ", omniSizeDescription=" + omniSizeDescription
                + ", contentState=" + contentState + ", completionDate="
                + completionDate + "]";
    }


}
