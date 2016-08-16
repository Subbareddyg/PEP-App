/**
 * 
 */
package com.belk.pep.vo;

/**
 * @author afusam1
 *
 */
public class RegularPetCopy {
	
	private String fromMDMId;
	
	private String toMDMId;
	
	private String fromOrinEntryType;
	
	private String toOrinEntryType;
	
	private String type;
	
	private boolean isSuccess = false;
	
	private String messageToDisplay;
	
	private String modifiedBy;

	/**
	 * @return the fromMDMId
	 */
	public String getFromMDMId() {
		return fromMDMId;
	}

	/**
	 * @param fromMDMId the fromMDMId to set
	 */
	public void setFromMDMId(String fromMDMId) {
		this.fromMDMId = fromMDMId;
	}

	/**
	 * @return the toMDMId
	 */
	public String getToMDMId() {
		return toMDMId;
	}

	/**
	 * @param toMDMId the toMDMId to set
	 */
	public void setToMDMId(String toMDMId) {
		this.toMDMId = toMDMId;
	}

	/**
	 * @return the fromOrinEntryType
	 */
	public String getFromOrinEntryType() {
		return fromOrinEntryType;
	}

	/**
	 * @param fromOrinEntryType the fromOrinEntryType to set
	 */
	public void setFromOrinEntryType(String fromOrinEntryType) {
		this.fromOrinEntryType = fromOrinEntryType;
	}

	/**
	 * @return the toOrinEntryType
	 */
	public String getToOrinEntryType() {
		return toOrinEntryType;
	}

	/**
	 * @param toOrinEntryType the toOrinEntryType to set
	 */
	public void setToOrinEntryType(String toOrinEntryType) {
		this.toOrinEntryType = toOrinEntryType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		if(("Style").equals(type)){
			this.type = "style";
		}else if(("Complex Pack").equals(type)){
			this.type = "complexpack";
		}else{
			this.type = type;
		}
	}

	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the messageToDisplay
	 */
	public String getMessageToDisplay() {
		return messageToDisplay;
	}

	/**
	 * @param messageToDisplay the messageToDisplay to set
	 */
	public void setMessageToDisplay(String messageToDisplay) {
		this.messageToDisplay = messageToDisplay;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



}
