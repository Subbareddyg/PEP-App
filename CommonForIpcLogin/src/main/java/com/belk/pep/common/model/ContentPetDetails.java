
package com.belk.pep.common.model;

import java.io.Serializable;

import com.belk.pep.common.userdata.UserData;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentPetDataObject.
 *
 * @author AFUSOS3
 */
public class ContentPetDetails implements Serializable {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new content pet data object.
	 */
	public ContentPetDetails() {
		
	}	
	
	
	/** The orin number. */
	private String orinNumber;	

	/** The content status. */
	private String contentStatus;	
	
	/** The vp user. */
	Common_Vpuser vpUser;	
	
	/** The belk user. */
	Common_BelkUser belkUser;
	
	/** The User data. */
	UserData UserData ;

	/**
	 * Gets the orin number.
	 *
	 * @return the orinNumber
	 */
	public String getOrinNumber() {
		return orinNumber;
	}

	/**
	 * Sets the orin number.
	 *
	 * @param orinNumber the orinNumber to set
	 */
	public void setOrinNumber(String orinNumber) {
		this.orinNumber = orinNumber;
	}

	

	/**
	 * Gets the vp user.
	 *
	 * @return the vpUser
	 */
	public Common_Vpuser getVpUser() {
		return vpUser;
	}

	/**
	 * Sets the vp user.
	 *
	 * @param vpUser the vpUser to set
	 */
	public void setVpUser(Common_Vpuser vpUser) {
		this.vpUser = vpUser;
	}

	/**
	 * Gets the belk user.
	 *
	 * @return the belkUser
	 */
	public Common_BelkUser getBelkUser() {
		return belkUser;
	}

	/**
	 * Sets the belk user.
	 *
	 * @param belkUser the belkUser to set
	 */
	public void setBelkUser(Common_BelkUser belkUser) {
		this.belkUser = belkUser;
	}

	/**
	 * Gets the user data.
	 *
	 * @return the userData
	 */
	public UserData getUserData() {
		return UserData;
	}

	/**
	 * Sets the user data.
	 *
	 * @param userData the userData to set
	 */
	public void setUserData(UserData userData) {
		UserData = userData;
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
	 * Sets the content status.
	 *
	 * @param contentStatus the contentStatus to set
	 */
	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContentPetDetails [orinNumber=" + orinNumber
				+ ", contentStatus=" + contentStatus + ", vpUser=" + vpUser
				+ ", belkUser=" + belkUser + ", UserData=" + UserData + "]";
	}

	
	
	
	

}
