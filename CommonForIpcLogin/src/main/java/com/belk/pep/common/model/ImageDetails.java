package com.belk.pep.common.model;

import java.io.Serializable;

import com.belk.pep.common.userdata.UserData;

/**
 * The Class ImageDetails.
 */
public class ImageDetails implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The orin number. */
	private String orinNumber;	
	
	/** The image status. */
	private String imageStatus;	
	
    private String pageNumber;
	/** The vp user. */
	Common_Vpuser vpUser;	
	
	/** The belk user. */
	Common_BelkUser belkUser;
	
	/** The User data. */
	UserData UserData ;
    public String getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
	/**
	 * Gets the user data.
	 *
	 * @return the user data
	 */
	public UserData getUserData() {
		return UserData;
	}

	/**
	 * Sets the user data.
	 *
	 * @param userData the new user data
	 */
	public void setUserData(UserData userData) {
		UserData = userData;
	}

	/**
	 * Gets the vp user.
	 *
	 * @return the vp user
	 */
	public Common_Vpuser getVpUser() {
		return vpUser;
	}

	/**
	 * Sets the vp user.
	 *
	 * @param vpUser the new vp user
	 */
	public void setVpUser(Common_Vpuser vpUser) {
		this.vpUser = vpUser;
	}

	/**
	 * Gets the belk user.
	 *
	 * @return the belk user
	 */
	public Common_BelkUser getBelkUser() {
		return belkUser;
	}

	/**
	 * Sets the belk user.
	 *
	 * @param belkUser the new belk user
	 */
	public void setBelkUser(Common_BelkUser belkUser) {
		this.belkUser = belkUser;
	}

	/**
	 * Gets the orin number.
	 *
	 * @return the orin number
	 */
	public String getOrinNumber() {
		return orinNumber;
	}

	/**
	 * Sets the orin number.
	 *
	 * @param orinNumber the new orin number
	 */
	public void setOrinNumber(String orinNumber) {
		this.orinNumber = orinNumber;
	}

	/**
	 * Gets the image status.
	 *
	 * @return the image status
	 */
	public String getImageStatus() {
		return imageStatus;
	}

	/**
	 * Sets the image status.
	 *
	 * @param imageStatus the new image status
	 */
	public void setImageStatus(String imageStatus) {
		this.imageStatus = imageStatus;
	}


}