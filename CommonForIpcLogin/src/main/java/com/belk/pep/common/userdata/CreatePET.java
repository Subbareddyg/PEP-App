package com.belk.pep.common.userdata;

import java.io.Serializable;

public class CreatePET implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String createPet;

	public String getCreatePet() {
		return createPet;
	}

	public void setCreatePet(String createPet) {
		this.createPet = createPet;
	}

}
