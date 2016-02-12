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

@NamedQuery(name="ExternalUser.findUser", query="from ExternalUser e where lower(e.id.supplierEmail) = ?1")
public class ExternalUser implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private ExternalUserPK id;

	/** The created by. */
	@Column(name="CREATED_BY")
	private String createdBy;

	/** The created date. */
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	/** The dept filter id. */
	@Column(name="DEPT_FILTER_ID")
	private String deptFilterId;

	/** The failed login attempts. */
	@Column(name="FAILED_LOGIN_ATTEMPTS")
	private BigDecimal failedLoginAttempts;

	/** The last login date time. */
	@Column(name="LAST_LOGIN_DATE_TIME")
	private Timestamp lastLoginDateTime;

	/** The pwd status. */
	@Column(name="PWD_STATUS")
	private String pwdStatus;

	/** The supplier pwd. */
	@Column(name="SUPPLIER_PWD")
	private String supplierPwd;

	/** The updated by. */
	@Column(name="UPDATED_BY")
	private String updatedBy;

	/** The updated date. */
	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	/** The vendor name. */
	@Column(name="VENDOR_NAME")
	private String vendorName;

	/** The pep role. */
	//bi-directional many-to-one association to PepRole
	@ManyToOne
	@JoinColumn(name="PEP_ROLE_ID")
	private PepRole pepRole;

	/**
	 * Instantiates a new external user.
	 */
	public ExternalUser() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public ExternalUserPK getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(ExternalUserPK id) {
		this.id = id;
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
	 * Gets the dept filter id.
	 *
	 * @return the dept filter id
	 */
	public String getDeptFilterId() {
		return this.deptFilterId;
	}

	/**
	 * Sets the dept filter id.
	 *
	 * @param deptFilterId the new dept filter id
	 */
	public void setDeptFilterId(String deptFilterId) {
		this.deptFilterId = deptFilterId;
	}

	/**
	 * Gets the failed login attempts.
	 *
	 * @return the failed login attempts
	 */
	public BigDecimal getFailedLoginAttempts() {
		return this.failedLoginAttempts;
	}

	/**
	 * Sets the failed login attempts.
	 *
	 * @param failedLoginAttempts the new failed login attempts
	 */
	public void setFailedLoginAttempts(BigDecimal failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
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
	 * Gets the pwd status.
	 *
	 * @return the pwd status
	 */
	public String getPwdStatus() {
		return this.pwdStatus;
	}

	/**
	 * Sets the pwd status.
	 *
	 * @param pwdStatus the new pwd status
	 */
	public void setPwdStatus(String pwdStatus) {
		this.pwdStatus = pwdStatus;
	}

	/**
	 * Gets the supplier pwd.
	 *
	 * @return the supplier pwd
	 */
	public String getSupplierPwd() {
		return this.supplierPwd;
	}

	/**
	 * Sets the supplier pwd.
	 *
	 * @param supplierPwd the new supplier pwd
	 */
	public void setSupplierPwd(String supplierPwd) {
		this.supplierPwd = supplierPwd;
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
	 * Gets the vendor name.
	 *
	 * @return the vendor name
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/**
	 * Sets the vendor name.
	 *
	 * @param vendorName the new vendor name
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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