package com.belk.pep.common.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;




/**
 * The  class for the VPUSER.
 * 
 */

public class Common_Vpuser implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private String pepUserID;

	/** The userid. */
	private String userid;

	/** The address. */
	private String address;

	/** The city. */
	private String city;

	/** The country. */
	private String country;

	/** The created by. */
	private String createdBy;

	/** The created date. */
	
	private Date createdDate;

	/** The first time user reset flag. */
	private String firstTimeUserResetFlag;

	/** The forgotten password flag. */
	private String forgottenPasswordFlag;

	/** The modified by. */
	private String modifiedBy;

	/** The modified date. */
	private Date modifiedDate;

	/** The state. */
	private String state;

	/** The user contact number. */
	private String userContactNumber;

	/** The user email address. */
	private String userEmailAddress;

	/** The user name. */
	private String userName;

	/** The user password. */
	private String userPassword;

	/** The user password status. */
	private String userPasswordStatus;

	/** The user type. */
	private String userType;

	/** The zipcode. */
	private String zipcode;

	/** The pet details. */
	
	
	 //MultiSupplierID Changes start
	private List<String> supplierIdsList;
	
		
	
	public List<String> getSupplierIdsList() {
		return supplierIdsList;
	}

	public void setSupplierIdsList(List<String> supplierIdsList) {
		this.supplierIdsList = supplierIdsList;
	}

	 //MultiSupplierID Changes end
	/**
	 * Instantiates a new vpuser.
	 */
	public Common_Vpuser() {
	}

	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	public String getUserid() {
		return this.userid;
	}

	/**
	 * Sets the userid.
	 *
	 * @param userid the new userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the first time user reset flag.
	 *
	 * @return the first time user reset flag
	 */
	public String getFirstTimeUserResetFlag() {
		return this.firstTimeUserResetFlag;
	}

	/**
	 * Sets the first time user reset flag.
	 *
	 * @param firstTimeUserResetFlag the new first time user reset flag
	 */
	public void setFirstTimeUserResetFlag(String firstTimeUserResetFlag) {
		this.firstTimeUserResetFlag = firstTimeUserResetFlag;
	}

	/**
	 * Gets the forgotten password flag.
	 *
	 * @return the forgotten password flag
	 */
	public String getForgottenPasswordFlag() {
		return this.forgottenPasswordFlag;
	}

	/**
	 * Sets the forgotten password flag.
	 *
	 * @param forgottenPasswordFlag the new forgotten password flag
	 */
	public void setForgottenPasswordFlag(String forgottenPasswordFlag) {
		this.forgottenPasswordFlag = forgottenPasswordFlag;
	}

	/**
	 * Gets the modified by.
	 *
	 * @return the modified by
	 */
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * Sets the modified by.
	 *
	 * @param modifiedBy the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Gets the modified date.
	 *
	 * @return the modified date
	 */
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * Sets the modified date.
	 *
	 * @param modifiedDate the new modified date
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the user contact number.
	 *
	 * @return the user contact number
	 */
	public String getUserContactNumber() {
		return this.userContactNumber;
	}

	/**
	 * Sets the user contact number.
	 *
	 * @param userContactNumber the new user contact number
	 */
	public void setUserContactNumber(String userContactNumber) {
		this.userContactNumber = userContactNumber;
	}

	/**
	 * Gets the user email address.
	 *
	 * @return the user email address
	 */
	public String getUserEmailAddress() {
		return this.userEmailAddress;
	}

	/**
	 * Sets the user email address.
	 *
	 * @param userEmailAddress the new user email address
	 */
	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the user password.
	 *
	 * @return the user password
	 */
	public String getUserPassword() {
		return this.userPassword;
	}

	/**
	 * Sets the user password.
	 *
	 * @param userPassword the new user password
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * Gets the user password status.
	 *
	 * @return the user password status
	 */
	public String getUserPasswordStatus() {
		return this.userPasswordStatus;
	}

	/**
	 * Sets the user password status.
	 *
	 * @param userPasswordStatus the new user password status
	 */
	public void setUserPasswordStatus(String userPasswordStatus) {
		this.userPasswordStatus = userPasswordStatus;
	}

	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * Sets the user type.
	 *
	 * @param userType the new user type
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Gets the zipcode.
	 *
	 * @return the zipcode
	 */
	public String getZipcode() {
		return this.zipcode;
	}

	/**
	 * Sets the zipcode.
	 *
	 * @param zipcode the new zipcode
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

    public String getPepUserID() {
        return pepUserID;
    }

    public void setPepUserID(String pepUserID) {
        this.pepUserID = pepUserID;
    }




}