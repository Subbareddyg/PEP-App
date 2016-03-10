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

	@Id
	@Column(name="BELK_ID")
	private String belkId;

	@Column(name="BELK_EMAIL")
	private String belkEmail;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="LAST_LOGIN_DATE_TIME")
	private Timestamp lastLoginDateTime;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to PepRole
	@ManyToOne
	@JoinColumn(name="PEP_ROLE_ID")
	private PepRole pepRole;

	public BelkUser() {
	}

	public String getBelkId() {
		return this.belkId;
	}

	public void setBelkId(String belkId) {
		this.belkId = belkId;
	}

	public String getBelkEmail() {
		return this.belkEmail;
	}

	public void setBelkEmail(String belkEmail) {
		this.belkEmail = belkEmail;
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

	public Timestamp getLastLoginDateTime() {
		return this.lastLoginDateTime;
	}

	public void setLastLoginDateTime(Timestamp lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
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