package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PEP_DEPARTMENT database table.
 * 
 */
@Embeddable
public class PepDepartmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The dept id. */
	@Column(name="DEPT_ID")
	private String deptId;

	/** The pep user id. */
	@Column(name="PEP_USER_ID")
	private String pepUserId;

	/**
	 * Instantiates a new pep department pk.
	 */
	public PepDepartmentPK() {
	}
	
	/**
	 * Gets the dept id.
	 *
	 * @return the dept id
	 */
	public String getDeptId() {
		return this.deptId;
	}
	
	/**
	 * Sets the dept id.
	 *
	 * @param deptId the new dept id
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	/**
	 * Gets the pep user id.
	 *
	 * @return the pep user id
	 */
	public String getPepUserId() {
		return this.pepUserId;
	}
	
	/**
	 * Sets the pep user id.
	 *
	 * @param pepUserId the new pep user id
	 */
	public void setPepUserId(String pepUserId) {
		this.pepUserId = pepUserId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PepDepartmentPK)) {
			return false;
		}
		PepDepartmentPK castOther = (PepDepartmentPK)other;
		return 
			this.deptId.equals(castOther.deptId)
			&& this.pepUserId.equals(castOther.pepUserId);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deptId.hashCode();
		hash = hash * prime + this.pepUserId.hashCode();
		
		return hash;
	}
}