package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the PET_LOCK database table.
 * 
 */
@Entity
@Table(name="PET_LOCK")
@NamedQueries(
    {
     @NamedQuery(name="PetLock.isPetLocked", query=" FROM PetLock p where (p.id.petId = :petId) and (p.pepFunction = :pepFunction)"),
     @NamedQuery(name="PetLock.deleteLockedPet", query="delete from PetLock p where (p.id.petId = :petId) and (p.pepFunction = :pepFunction)")
    } 
)

public class PetLock implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PetLockPK id;

	@Column(name="LOCK_STATUS")
	private String lockStatus;

	@Column(name="PEP_FUNCTION")
	private String pepFunction;

	@Column(name="RELEASE_DATE")
	private Timestamp releaseDate;

	public PetLock() {
	}

	public PetLockPK getId() {
		return this.id;
	}

	public void setId(PetLockPK id) {
		this.id = id;
	}

	public String getLockStatus() {
		return this.lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getPepFunction() {
		return this.pepFunction;
	}

	public void setPepFunction(String pepFunction) {
		this.pepFunction = pepFunction;
	}

	public Timestamp getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

}