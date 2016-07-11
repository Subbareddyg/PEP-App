package com.belk.pep.util;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PET_LOCK database table.
 * 
 */
@Embeddable
public class PetLockPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PET_ID")
	private String petId;

	@Column(name="PEP_USER")
	private String pepUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOCK_DATE")
	private java.util.Date lockDate;

	public PetLockPK() {
		//Default Constructor
	}
	public String getPetId() {
		return this.petId;
	}
	public void setPetId(String petId) {
		this.petId = petId;
	}
	public String getPepUser() {
		return this.pepUser;
	}
	public void setPepUser(String pepUser) {
		this.pepUser = pepUser;
	}
	public java.util.Date getLockDate() {
		return this.lockDate;
	}
	public void setLockDate(java.util.Date lockDate) {
		this.lockDate = lockDate;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PetLockPK)) {
			return false;
		}
		PetLockPK castOther = (PetLockPK)other;
		return 
			this.petId.equals(castOther.petId)
			&& this.pepUser.equals(castOther.pepUser)
			&& this.lockDate.equals(castOther.lockDate);
	}
@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.petId.hashCode();
		hash = hash * prime + this.pepUser.hashCode();
		hash = hash * prime + this.lockDate.hashCode();
		
		return hash;
	}
}