package com.belk.pep.common.userdata;

import java.io.Serializable;

import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.model.Common_Vpuser;

/**
 * The Class UserData.
 */
public class UserData implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The is external. */
	boolean isExternal;
	
	/** The is internal. */
	boolean isInternal;
	
	/** The vp user. */
	Common_Vpuser vpUser;
	
	/** The belk user. */
	Common_BelkUser belkUser;

	
	/**
	 * Checks if is external.
	 *
	 * @return true, if is external
	 */
	public boolean isExternal() {
		return isExternal;
	}
	
	/**
	 * Sets the external.
	 *
	 * @param isExternal the new external
	 */
	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}
	
	/**
	 * Checks if is internal.
	 *
	 * @return true, if is internal
	 */
	public boolean isInternal() {
		return isInternal;
	}
	
	/**
	 * Sets the internal.
	 *
	 * @param isInternal the new internal
	 */
	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
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
	
	
	

	/** The role name. */
	String roleName;
	
	/** The access right. */
	String accessRight;


	/**
	 * Gets the role name.
	 *
	 * @return the role name
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the role name.
	 *
	 * @param roleName the new role name
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Gets the access right.
	 *
	 * @return the access right
	 */
	public String getAccessRight() {
		return accessRight;
	}

	/**
	 * Sets the access right.
	 *
	 * @param accessRight the new access right
	 */
	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
	@Override
	public String toString() {
		return "UserData [isExternal=" + isExternal + ", isInternal="
				+ isInternal + ", vpUser=" + vpUser + ", belkUser=" + belkUser
				+ ", roleName=" + roleName + ", accessRight=" + accessRight
				+ "]";
	}
	

	
	

}
