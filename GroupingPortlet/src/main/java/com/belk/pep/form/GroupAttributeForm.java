package com.belk.pep.form;




/**
 * This class is used to save the Group Header data as a Form data.
 * @author AFUPYB3
 *
 */
public class GroupAttributeForm {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
    //Form data
    private String orinNumber;
    private String styleNumber;
    //XML Shredding
    private String prodName;
    private String colorCode;
    private String colorName;
    private String size;
    private String sizeCode;
    //Already in Group
    private String isAlreadyInGroup;
    private String isAlreadyInSameGroup;
    //isDefault
    private String isDefault;
    private String petStatus;
    private String entryType;
    // parent MDMID
    private String parentMdmid;
    private String componentStyleId;
    private String classId;
    
    private String isGroup;
    private String priority;
    private String haveChildGroup;
    private String defaultColor;
	/**
	 * @return the haveChildGroup
	 */
	public String getHaveChildGroup() {
		return haveChildGroup;
	}
	/**
	 * @param haveChildGroup the haveChildGroup to set
	 */
	public void setHaveChildGroup(String haveChildGroup) {
		this.haveChildGroup = haveChildGroup;
	}
    
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the isGroup
	 */
	public String getIsGroup() {
		return isGroup;
	}
	/**
	 * @param isGroup the isGroup to set
	 */
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
    
    /**
     * @return the orinNumber
     */
    public String getOrinNumber() {
        return orinNumber;
    }
    /**
     * @param orinNumber the orinNumber to set
     */
    public void setOrinNumber(String orinNumber) {
        this.orinNumber = orinNumber;
    }
    /**
     * @return the styleNumber
     */
    public String getStyleNumber() {
        return styleNumber;
    }
    /**
     * @param styleNumber the styleNumber to set
     */
    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }
    /**
     * @return the prodName
     */
    public String getProdName() {
        return prodName;
    }
    /**
     * @param prodName the prodName to set
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }
    /**
     * @param colorCode the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
    /**
     * @return the colorName
     */
    public String getColorName() {
        return colorName;
    }
    /**
     * @param colorName the colorName to set
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    /**
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
     * @return the isAlreadyInGroup
     */
    public String getIsAlreadyInGroup() {
        return isAlreadyInGroup;
    }
    /**
     * @param isAlreadyInGroup the isAlreadyInGroup to set
     */
    public void setIsAlreadyInGroup(String isAlreadyInGroup) {
        this.isAlreadyInGroup = isAlreadyInGroup;
    }
    /**
     * @return the isDefault
     */
    public String getIsDefault() {
        return isDefault;
    }
    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
	/**
	 * @return the petStatus
	 */
	public String getPetStatus() {
		return petStatus;
	}
	/**
	 * @param petStatus the petStatus to set
	 */
	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
	}
	/**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}
	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	/**
	 * @return the parentMdmid
	 */
	public String getParentMdmid() {
		return parentMdmid;
	}
	/**
	 * @param parentMdmid the parentMdmid to set
	 */
	public void setParentMdmid(String parentMdmid) {
		this.parentMdmid = parentMdmid;
	}
	/**
	 * @return the componentStyleId
	 */
	public String getComponentStyleId() {
		return componentStyleId;
	}
	/**
	 * @param componentStyleId the componentStyleId to set
	 */
	public void setComponentStyleId(String componentStyleId) {
		this.componentStyleId = componentStyleId;
	}
	/**
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * @return the isAlreadyInSameGroup
	 */
	public String getIsAlreadyInSameGroup() {
		return isAlreadyInSameGroup;
	}
	/**
	 * @param isAlreadyInSameGroup the isAlreadyInSameGroup to set
	 */
	public void setIsAlreadyInSameGroup(String isAlreadyInSameGroup) {
		this.isAlreadyInSameGroup = isAlreadyInSameGroup;
	}
	/**
	 * @return the sizeCode
	 */
	public String getSizeCode() {
		return sizeCode;
	}
	/**
	 * @param sizeCode the sizeCode to set
	 */
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	/**
	 * @return the defaultColor
	 */
	public String getDefaultColor() {
		return defaultColor;
	}
	/**
	 * @param defaultColor the defaultColor to set
	 */
	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}
    
  }
