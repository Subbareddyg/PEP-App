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
@NamedQuery(name="PepRole.findAll", query="SELECT p FROM PepRole p")
public class PepRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PEP_ROLE_ID")
	private String pepRoleId;

	@Column(name="ACCESS_TYPE")
	private String accessType;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="ROLE_STATUS")
	private String roleStatus;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name="USER_TYPE")
	private String userType;

	//bi-directional many-to-one association to BelkUser
	@OneToMany(mappedBy="pepRole")
	private List<BelkUser> belkUsers;

	//bi-directional many-to-one association to ExternalUser
	@OneToMany(mappedBy="pepRole")
	private List<ExternalUser> externalUsers;

	public PepRole() {
	}

	public String getPepRoleId() {
		return this.pepRoleId;
	}

	public void setPepRoleId(String pepRoleId) {
		this.pepRoleId = pepRoleId;
	}

	public String getAccessType() {
		return this.accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getRoleStatus() {
		return this.roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<BelkUser> getBelkUsers() {
		return this.belkUsers;
	}

	public void setBelkUsers(List<BelkUser> belkUsers) {
		this.belkUsers = belkUsers;
	}

	public BelkUser addBelkUser(BelkUser belkUser) {
		getBelkUsers().add(belkUser);
		belkUser.setPepRole(this);

		return belkUser;
	}

	public BelkUser removeBelkUser(BelkUser belkUser) {
		getBelkUsers().remove(belkUser);
		belkUser.setPepRole(null);

		return belkUser;
	}

	public List<ExternalUser> getExternalUsers() {
		return this.externalUsers;
	}

	public void setExternalUsers(List<ExternalUser> externalUsers) {
		this.externalUsers = externalUsers;
	}

	public ExternalUser addExternalUser(ExternalUser externalUser) {
		getExternalUsers().add(externalUser);
		externalUser.setPepRole(this);

		return externalUser;
	}

	public ExternalUser removeExternalUser(ExternalUser externalUser) {
		getExternalUsers().remove(externalUser);
		externalUser.setPepRole(null);

		return externalUser;
	}

}