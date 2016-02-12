package com.belk.pep.model;

/**
 * @author 
 *
 */


public class UserObject {
	
	private String lanID ;
    private String emailId;
    private String pepRoleId;
    private String roleAccessType;
    private String pepRoleStatus;
    private String userType ;
	
	
    /**
     * @return the lanID
     */
    public String getLanID() {
        return lanID;
    }
    /**
     * @param lanID the lanID to set
     */
    public void setLanID(String lanID) {
        this.lanID = lanID;
    }
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
    /**
     * @return the pepRoleId
     */
    public String getPepRoleId() {
        return pepRoleId;
    }
    /**
     * @param pepRoleId the pepRoleId to set
     */
    public void setPepRoleId(String pepRoleId) {
        this.pepRoleId = pepRoleId;
    }
    /**
     * @return the roleAccessType
     */
    public String getRoleAccessType() {
        return roleAccessType;
    }
    /**
     * @param roleAccessType the roleAccessType to set
     */
    public void setRoleAccessType(String roleAccessType) {
        this.roleAccessType = roleAccessType;
    }
    /**
     * @return the pepRoleStatus
     */
    public String getPepRoleStatus() {
        return pepRoleStatus;
    }
    /**
     * @param pepRoleStatus the pepRoleStatus to set
     */
    public void setPepRoleStatus(String pepRoleStatus) {
        this.pepRoleStatus = pepRoleStatus;
    }
    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }
    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
	
	
	

}
