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

	@Id
	@Column(name="CONFIG_ID")
	private String configId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	private String param;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name="\"VALUE\"")
	private String value;

	public Config() {
	}

	public String getConfigId() {
		return this.configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Config [configId=" + configId + ", createdBy=" + createdBy
            + ", createdDate=" + createdDate + ", param=" + param
            + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
            + ", value=" + value + "]";
    }

}