package com.belk.pep.model;

import java.io.Serializable;

import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.model.Common_Vpuser;

public class CommonData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isExternal;
	boolean isInternal;
	private String lanId;
	private String selectedOrin;	
	private String imageStatus;	
	private String roleName;
	private String accessRight;
	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getLanId() {
		return lanId;
	}
	public void setLanId(String lanId) {
		this.lanId = lanId;
	}
	
	public boolean isExternal() {
		return isExternal;
	}
	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}
	public boolean isInternal() {
		return isInternal;
	}
	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}
	public String getSelectedOrin() {
		return selectedOrin;
	}
	public void setSelectedOrin(String selectedOrin) {
		this.selectedOrin = selectedOrin;
	}
	public String getImageStatus() {
		return imageStatus;
	}
	public void setImageStatus(String imageStatus) {
		this.imageStatus = imageStatus;
	}
	
	
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
	@Override
	public String toString() {
		return "UserData [isExternal=" + isExternal + ", isInternal="
				+ isInternal 
				+ ", roleName=" + roleName + ", accessRight=" + accessRight
				+ "]";
	}
	

	
	

}
