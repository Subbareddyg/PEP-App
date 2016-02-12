
package com.belk.pep.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AFUSOS3
 *
 */
public class StyleInformationVO  implements Serializable{

    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The orin. */
    private String orin;
    
    /** The grouping. */
    private String grouping;
    
    /** The department id. */
    private String departmentId;    
    
    /** The vendor name. */
    private String  vendorName;    
    
   
    /** The style. */
    private String  style;
    
    /** The style id. */
    private String  styleId;
    
    /** The class id. */
    private String  classId;  
    
    /** The vendor id. */
    private String  vendorId;
    
    /** The vendor sample indicator. */
    private String vendorSampleIndicator;
    
    /** The entry type. */
    private String  entryType;;
    
 

    /** The department class. */
    private String departmentClass;
    
    /** The vendor number. */
    private String vendorNumber;

    /** The description. */
    private String  description;
    
    
    /** The omni channel vendor indicator. */
    private String omniChannelVendorIndicator;
    
    
    /** The vendor provided image indicator. */
    private String vendorProvidedImageIndicator;
    
    /** The completion date. */
    private Date completionDate;
    
    /** The completion date of style. */
    private String  completionDateOfStyle;

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
     * Gets the grouping.
     *
     * @return the grouping
     */
    public String getGrouping() {
        return grouping;
    }

    /**
     * Sets the grouping.
     *
     * @param grouping the grouping to set
     */
    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    /**
     * Gets the department id.
     *
     * @return the departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department id.
     *
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the vendor name.
     *
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Sets the vendor name.
     *
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Gets the style.
     *
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style.
     *
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Gets the department class.
     *
     * @return the departmentClass
     */
    public String getDepartmentClass() {
        return departmentClass;
    }

    /**
     * Sets the department class.
     *
     * @param departmentClass the departmentClass to set
     */
    public void setDepartmentClass(String departmentClass) {
        this.departmentClass = departmentClass;
    }

    /**
     * Gets the vendor number.
     *
     * @return the vendorNumber
     */
    public String getVendorNumber() {
        return vendorNumber;
    }

    /**
     * Sets the vendor number.
     *
     * @param vendorNumber the vendorNumber to set
     */
    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the omni channel vendor indicator.
     *
     * @return the omniChannelVendorIndicator
     */
    public String getOmniChannelVendorIndicator() {
        return omniChannelVendorIndicator;
    }

    /**
     * Sets the omni channel vendor indicator.
     *
     * @param omniChannelVendorIndicator the omniChannelVendorIndicator to set
     */
    public void setOmniChannelVendorIndicator(String omniChannelVendorIndicator) {
        this.omniChannelVendorIndicator = omniChannelVendorIndicator;
    }

    /**
     * Gets the vendor provided image indicator.
     *
     * @return the vendorProvidedImageIndicator
     */
    public String getVendorProvidedImageIndicator() {
        return vendorProvidedImageIndicator;
    }

    /**
     * Sets the vendor provided image indicator.
     *
     * @param vendorProvidedImageIndicator the vendorProvidedImageIndicator to set
     */
    public void setVendorProvidedImageIndicator(String vendorProvidedImageIndicator) {
        this.vendorProvidedImageIndicator = vendorProvidedImageIndicator;
    }

    /**
     * Gets the completion date.
     *
     * @return the completionDate
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date.
     *
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
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
     * Sets the style id.
     *
     * @param styleId the styleId to set
     */
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }
    
    /**
     * Gets the class id.
     *
     * @return the classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * Sets the class id.
     *
     * @param classId the classId to set
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the vendorSampleIndicator
     */
    public String getVendorSampleIndicator() {
        return vendorSampleIndicator;
    }

    /**
     * @param vendorSampleIndicator the vendorSampleIndicator to set
     */
    public void setVendorSampleIndicator(String vendorSampleIndicator) {
        this.vendorSampleIndicator = vendorSampleIndicator;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    /**
     * Gets the completion date of style.
     *
     * @return the completionDateOfStyle
     */
    public String getCompletionDateOfStyle() {
        return completionDateOfStyle;
    }

    /**
     * Sets the completion date of style.
     *
     * @param completionDateOfStyle the completionDateOfStyle to set
     */
    public void setCompletionDateOfStyle(String completionDateOfStyle) {
        this.completionDateOfStyle = completionDateOfStyle;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StyleInformationVO [orin=" + orin + ", grouping=" + grouping
            + ", departmentId=" + departmentId + ", vendorName=" + vendorName
            + ", style=" + style + ", styleId=" + styleId + ", classId="
            + classId + ", vendorId=" + vendorId + ", vendorSampleIndicator="
            + vendorSampleIndicator + ", entryType=" + entryType
            + ", departmentClass=" + departmentClass + ", vendorNumber="
            + vendorNumber + ", description=" + description
            + ", omniChannelVendorIndicator=" + omniChannelVendorIndicator
            + ", vendorProvidedImageIndicator=" + vendorProvidedImageIndicator
            + ", completionDate=" + completionDate + ", completionDateOfStyle="
            + completionDateOfStyle + "]";
    }

  
   

}
