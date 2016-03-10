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

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="PEP_USER_ID")
	private String pepUserId;

	public PepDepartmentPK() {
	}
	public String getDeptId() {
		return this.deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getPepUserId() {
		return this.pepUserId;
	}
	public void setPepUserId(String pepUserId) {
		this.pepUserId = pepUserId;
	}

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

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deptId.hashCode();
		hash = hash * prime + this.pepUserId.hashCode();
		
		return hash;
	}
}