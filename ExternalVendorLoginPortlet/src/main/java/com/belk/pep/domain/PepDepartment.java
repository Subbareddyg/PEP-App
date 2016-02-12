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

	/** The id. */
	@EmbeddedId
	private PepDepartmentPK id;

	/** The created by. */
	@Column(name="CREATED_BY")
	private String createdBy;

	/** The created date. */
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	/** The dept name. */
	@Column(name="DEPT_NAME")
	private String deptName;

	/** The updated by. */
	@Column(name="UPDATED_BY")
	private String updatedBy;

	/** The updated date. */
	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	/**
	 * Instantiates a new pep department.
	 */
	public PepDepartment() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public PepDepartmentPK getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(PepDepartmentPK id) {
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
	 * Gets the dept name.
	 *
	 * @return the dept name
	 */
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * Sets the dept name.
	 *
	 * @param deptName the new dept name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

}