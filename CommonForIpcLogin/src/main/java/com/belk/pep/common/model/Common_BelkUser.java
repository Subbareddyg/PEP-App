/*
 * 
 */
package com.belk.pep.common.model;

import java.io.Serializable;
import java.util.Date;



/**
 * The class for the BELK_USER  .
 * 
 */

public class Common_BelkUser implements Serializable {
	private static final long serialVersionUID = 1L;


	private String lanId;

	/** The address. */
	private String address;

	/** The city. */
	private String city;

	private String contactNumber;

	/** The country. */
	private String country;


	private String createdBy;


	private Date createdDate;

	/** The email address. */

	private String emailAddress;

	/** The modified by. */
	private String modifiedBy;

	/** The modified date. */
	
	private Date modifiedDate;

	/** The password. */
	private String password;

	/** The state. */

	private String state;

	/** The zipcode. */
	private String zipcode;

	

	/**
	 * Instantiates a new belk user.
	 */
	public Common_BelkUser() {
	}

	/**
	 * Gets the lan id.
	 *
	 * @return the lan id
	 */
	public String getLanId() {
		return this.lanId;
	}

	/**
	 * Sets the lan id.
	 *
	 * @param lanId the new lan id
	 */
	public void setLanId(String lanId) {
		this.lanId = lanId;
	}

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
	 * Gets the contact number.
	 *
	 * @return the contact number
	 */
	public String getContactNumber() {
		return this.contactNumber;
	}

	/**
	 * Sets the contact number.
	 *
	 * @param contactNumber the new contact number
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
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

	

}