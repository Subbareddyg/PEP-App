package com.belk.pep.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the PET_ATTRIBUTE database table.
 * 
 */
@Entity
@Table(name="PET_ATTRIBUTE")
@NamedQuery(name="PetAttribute.findAll", query="SELECT p FROM PetAttribute p")
public class PetAttribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ATTR_ID")
	private String attrId;

	@Column(name="ATTRIBUTE_NAME")
	private String attributeName;

	@Column(name="CATEGORY_ID")
	private String categoryId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="DISPLAY_NAME")
	private String displayName;

	private String displayable;

	private String editable;

	@Column(name="HTML_DISPLAY_CODE")
	private String htmlDisplayCode;

	@Column(name="HTML_DISPLAY_DESC")
	private String htmlDisplayDesc;

	private String mandatory;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	public PetAttribute() {
	}

	public String getAttrId() {
		return this.attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayable() {
		return this.displayable;
	}

	public void setDisplayable(String displayable) {
		this.displayable = displayable;
	}

	public String getEditable() {
		return this.editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getHtmlDisplayCode() {
		return this.htmlDisplayCode;
	}

	public void setHtmlDisplayCode(String htmlDisplayCode) {
		this.htmlDisplayCode = htmlDisplayCode;
	}

	public String getHtmlDisplayDesc() {
		return this.htmlDisplayDesc;
	}

	public void setHtmlDisplayDesc(String htmlDisplayDesc) {
		this.htmlDisplayDesc = htmlDisplayDesc;
	}

	public String getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

    @Override
    public String toString() {
        return "PetAttribute [attrId=" + attrId + ", attributeName="
            + attributeName + ", categoryId=" + categoryId + ", createdBy="
            + createdBy + ", createdDate=" + createdDate + ", displayName="
            + displayName + ", displayable=" + displayable + ", editable="
            + editable + ", htmlDisplayCode=" + htmlDisplayCode
            + ", htmlDisplayDesc=" + htmlDisplayDesc + ", mandatory="
            + mandatory + ", modifiedBy=" + modifiedBy + ", modifiedDate="
            + modifiedDate + "]";
    }

}