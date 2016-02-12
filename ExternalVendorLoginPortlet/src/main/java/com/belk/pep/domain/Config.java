package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the CONFIG database table.
 * 
 */
@Entity
@NamedQuery(name="Config.findAll", query="SELECT c FROM Config c")
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The config id. */
	@Id
	@Column(name="CONFIG_ID")
	private String configId;

	/** The created by. */
	@Column(name="CREATED_BY")
	private String createdBy;

	/** The created date. */
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	/** The param. */
	private String param;

	/** The updated by. */
	@Column(name="UPDATED_BY")
	private String updatedBy;

	/** The updated date. */
	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	/** The value. */
	@Column(name="\"VALUE\"")
	private String value;

	/**
	 * Instantiates a new config.
	 */
	public Config() {
	}

	/**
	 * Gets the config id.
	 *
	 * @return the config id
	 */
	public String getConfigId() {
		return this.configId;
	}

	/**
	 * Sets the config id.
	 *
	 * @param configId the new config id
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
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
	 * Gets the param.
	 *
	 * @return the param
	 */
	public String getParam() {
		return this.param;
	}

	/**
	 * Sets the param.
	 *
	 * @param param the new param
	 */
	public void setParam(String param) {
		this.param = param;
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

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}