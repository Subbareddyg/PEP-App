package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the BELK_USER database table.
 * 
 */
@Entity
@Table(name="BELK_USER")
@NamedQuery(name="BelkUser.findAll", query="SELECT b FROM BelkUser b")
public class BelkUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The belk id. */
	@Id
	@Column(name="BELK_ID")
	private String belkId;

	/** The belk email. */
	@Column(name="BELK_EMAIL")
	private String belkEmail;

	/** The created by. */
	@Column(name="CREATED_BY")
	private String createdBy;

	/** The created date. */
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	/** The last login date time. */
	@Column(name="LAST_LOGIN_DATE_TIME")
	private Timestamp lastLoginDateTime;

	/** The updated by. */
	@Column(name="UPDATED_BY")
	private String updatedBy;

	/** The updated date. */
	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	/** The pep role. */
	//bi-directional many-to-one association to PepRole
	@ManyToOne
	@JoinColumn(name="PEP_ROLE_ID")
	private PepRole pepRole;

	/**
	 * Instantiates a new belk user.
	 */
	public BelkUser() {
	}

	/**
	 * Gets the belk id.
	 *
	 * @return the belk id
	 */
	public String getBelkId() {
		return this.belkId;
	}

	/**
	 * Sets the belk id.
	 *
	 * @param belkId the new belk id
	 */
	public void setBelkId(String belkId) {
		this.belkId = belkId;
	}

	/**
	 * Gets the belk email.
	 *
	 * @return the belk email
	 */
	public String getBelkEmail() {
		return this.belkEmail;
	}

	/**
	 * Sets the belk email.
	 *
	 * @param belkEmail the new belk email
	 */
	public void setBelkEmail(String belkEmail) {
		this.belkEmail = belkEmail;
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
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last login date time.
	 *
	 * @return the last login date time
	 */
	public Timestamp getLastLoginDateTime() {
		return this.lastLoginDateTime;
	}

	/**
	 * Sets the last login date time.
	 *
	 * @param lastLoginDateTime the new last login date time
	 */
	public void setLastLoginDateTime(Timestamp lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date.
	 *
	 * @return the updated date
	 */
	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	/**
	 * Sets the updated date.
	 *
	 * @param updatedDate the new updated date
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * Gets the pep role.
	 *
	 * @return the pep role
	 */
	public PepRole getPepRole() {
		return this.pepRole;
	}

	/**
	 * Sets the pep role.
	 *
	 * @param pepRole the new pep role
	 */
	public void setPepRole(PepRole pepRole) {
		this.pepRole = pepRole;
	}

}