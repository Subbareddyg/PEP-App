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

	/** The supplier id. */
	@Column(name="SUPPLIER_ID")
	private String supplierId;

	/** The supplier email. */
	@Column(name="SUPPLIER_EMAIL")
	private String supplierEmail;

	/**
	 * Instantiates a new external user pk.
	 */
	public ExternalUserPK() {
	}
	
	/**
	 * Gets the supplier id.
	 *
	 * @return the supplier id
	 */
	public String getSupplierId() {
		return this.supplierId;
	}
	
	/**
	 * Sets the supplier id.
	 *
	 * @param supplierId the new supplier id
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	/**
	 * Gets the supplier email.
	 *
	 * @return the supplier email
	 */
	public String getSupplierEmail() {    
	    
		return this.supplierEmail;
	}
	
	/**
	 * Sets the supplier email.
	 *
	 * @param supplierEmail the new supplier email
	 */
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
			&& this.supplierEmail.equals(castOther.supplierEmail);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.supplierId.hashCode();
		hash = hash * prime + this.supplierEmail.hashCode();
		
		return hash;
	}
}