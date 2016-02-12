package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the PEP_DEPARTMENT database table.
 * 
 */
@Entity
@Table(name="PEP_DEPARTMENT")
@NamedQuery(name="PepDepartment.findAll", query="SELECT p FROM PepDepartment p")
public class PepDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PepDepartmentPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="DEPT_NAME")
	private String deptName;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	public PepDepartment() {
	}

	public PepDepartmentPK getId() {
		return this.id;
	}

	public void setId(PepDepartmentPK id) {
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

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

}