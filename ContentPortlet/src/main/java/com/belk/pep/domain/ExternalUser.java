package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the EXTERNAL_USER database table.
 * 
 */
@Entity
@Table(name="EXTERNAL_USER")
@NamedQuery(name="ExternalUser.findAll", query="SELECT e FROM ExternalUser e")
public class ExternalUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExternalUserPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="FAILED_LOGIN_ATTEMPTS")
	private BigDecimal failedLoginAttempts;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_LOGIN_DATE_TIME")
	private Timestamp lastLoginDateTime;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="PRIMARY_EMAIL")
	private String primaryEmail;

	@Column(name="PWD_STATUS")
	private String pwdStatus;

	@Column(name="SUPPLIER_PWD")
	private String supplierPwd;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to PepRole
	@ManyToOne
	@JoinColumn(name="PEP_ROLE_ID")
	private PepRole pepRole;

	public ExternalUser() {
	}

	public ExternalUserPK getId() {
		return this.id;
	}

	public void setId(ExternalUserPK id) {
		this.id = id;
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

	public BigDecimal getFailedLoginAttempts() {
		return this.failedLoginAttempts;
	}

	public void setFailedLoginAttempts(BigDecimal failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Timestamp getLastLoginDateTime() {
		return this.lastLoginDateTime;
	}

	public void setLastLoginDateTime(Timestamp lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryEmail() {
		return this.primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getPwdStatus() {
		return this.pwdStatus;
	}

	public void setPwdStatus(String pwdStatus) {
		this.pwdStatus = pwdStatus;
	}

	public String getSupplierPwd() {
		return this.supplierPwd;
	}

	public void setSupplierPwd(String supplierPwd) {
		this.supplierPwd = supplierPwd;
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

	public PepRole getPepRole() {
		return this.pepRole;
	}

	public void setPepRole(PepRole pepRole) {
		this.pepRole = pepRole;
	}

}