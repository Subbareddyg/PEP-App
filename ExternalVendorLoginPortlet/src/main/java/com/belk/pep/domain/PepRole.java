package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PEP_ROLE database table.
 * 
 */
@Entity
@Table(name="PEP_ROLE")
@NamedQuery(name="PepRole.findRoleDetails", query="SELECT p FROM PepRole p where p.pepRoleId = :peproleid")
public class PepRole implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pep role id. */
	@Id
	@Column(name="PEP_ROLE_ID")
	private String pepRoleId;

	/** The access type. */
	@Column(name="ACCESS_TYPE")
	private String accessType;

	/** The created by. */
	@Column(name="CREATED_BY")
	private String createdBy;

	/** The created date. */
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	/** The role status. */
	@Column(name="ROLE_STATUS")
	private String roleStatus;

	/** The updated by. */
	@Column(name="UPDATED_BY")
	private String updatedBy;

	/** The updated date. */
	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	/** The user type. */
	@Column(name="USER_TYPE")
	private String userType;

	/** The belk users. */
	//bi-directional many-to-one association to BelkUser
	@OneToMany(mappedBy="pepRole")
	private List<BelkUser> belkUsers;

	/** The external users. */
	//bi-directional many-to-one association to ExternalUser
	@OneToMany(mappedBy="pepRole")
	private List<ExternalUser> externalUsers;

	/**
	 * Instantiates a new pep role.
	 */
	public PepRole() {
	}

	/**
	 * Gets the pep role id.
	 *
	 * @return the pep role id
	 */
	public String getPepRoleId() {
		return this.pepRoleId;
	}

	/**
	 * Sets the pep role id.
	 *
	 * @param pepRoleId the new pep role id
	 */
	public void setPepRoleId(String pepRoleId) {
		this.pepRoleId = pepRoleId;
	}

	/**
	 * Gets the access type.
	 *
	 * @return the access type
	 */
	public String getAccessType() {
		return this.accessType;
	}

	/**
	 * Sets the access type.
	 *
	 * @param accessType the new access type
	 */
	public void setAccessType(String accessType) {
		this.accessType = accessType;
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
	 * Gets the role status.
	 *
	 * @return the role status
	 */
	public String getRoleStatus() {
		return this.roleStatus;
	}

	/**
	 * Sets the role status.
	 *
	 * @param roleStatus the new role status
	 */
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
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
	 * Gets the belk users.
	 *
	 * @return the belk users
	 */
	public List<BelkUser> getBelkUsers() {
		return this.belkUsers;
	}

	/**
	 * Sets the belk users.
	 *
	 * @param belkUsers the new belk users
	 */
	public void setBelkUsers(List<BelkUser> belkUsers) {
		this.belkUsers = belkUsers;
	}

	/**
	 * Adds the belk user.
	 *
	 * @param belkUser the belk user
	 * @return the belk user
	 */
	public BelkUser addBelkUser(BelkUser belkUser) {
		getBelkUsers().add(belkUser);
		belkUser.setPepRole(this);

		return belkUser;
	}

	/**
	 * Removes the belk user.
	 *
	 * @param belkUser the belk user
	 * @return the belk user
	 */
	public BelkUser removeBelkUser(BelkUser belkUser) {
		getBelkUsers().remove(belkUser);
		belkUser.setPepRole(null);

		return belkUser;
	}

	/**
	 * Gets the external users.
	 *
	 * @return the external users
	 */
	public List<ExternalUser> getExternalUsers() {
		return this.externalUsers;
	}

	/**
	 * Sets the external users.
	 *
	 * @param externalUsers the new external users
	 */
	public void setExternalUsers(List<ExternalUser> externalUsers) {
		this.externalUsers = externalUsers;
	}

	/**
	 * Adds the external user.
	 *
	 * @param externalUser the external user
	 * @return the external user
	 */
	public ExternalUser addExternalUser(ExternalUser externalUser) {
		getExternalUsers().add(externalUser);
		externalUser.setPepRole(this);

		return externalUser;
	}

	/**
	 * Removes the external user.
	 *
	 * @param externalUser the external user
	 * @return the external user
	 */
	public ExternalUser removeExternalUser(ExternalUser externalUser) {
		getExternalUsers().remove(externalUser);
		externalUser.setPepRole(null);

		return externalUser;
	}

}