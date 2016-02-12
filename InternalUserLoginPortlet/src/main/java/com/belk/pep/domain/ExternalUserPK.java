package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the EXTERNAL_USER database table.
 * 
 */
@Embeddable
public class ExternalUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SUPPLIER_ID")
	private String supplierId;

	@Column(name="SUPPLIER_EMAIL")
	private String supplierEmail;

	@Column(name="DEPT_FILTER_ID")
	private String deptFilterId;

	public ExternalUserPK() {
	}
	public String getSupplierId() {
		return this.supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierEmail() {
		return this.supplierEmail;
	}
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
	public String getDeptFilterId() {
		return this.deptFilterId;
	}
	public void setDeptFilterId(String deptFilterId) {
		this.deptFilterId = deptFilterId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExternalUserPK)) {
			return false;
		}
		ExternalUserPK castOther = (ExternalUserPK)other;
		return 
			this.supplierId.equals(castOther.supplierId)
			&& this.supplierEmail.equals(castOther.supplierEmail)
			&& this.deptFilterId.equals(castOther.deptFilterId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.supplierId.hashCode();
		hash = hash * prime + this.supplierEmail.hashCode();
		hash = hash * prime + this.deptFilterId.hashCode();
		
		return hash;
	}
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ExternalUserPK [supplierId=" + supplierId + ", supplierEmail="
            + supplierEmail + ", deptFilterId=" + deptFilterId + "]";
    }
}