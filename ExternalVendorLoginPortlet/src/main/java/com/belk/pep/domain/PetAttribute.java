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

	@Column(name="ATTRIBUTE_PATH")
	private String attributePath;

	@Column(name="CATEGORY_ID")
	private String categoryId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="DISPLAY_NAME")
	private String displayName;

	@Column(name="DISPLAY_ORDER")
	private String displayOrder;

	private String displayable;

	private String editable;

	@Column(name="HTML_DISPLAY_CODE")
	private String htmlDisplayCode;

	@Column(name="HTML_DISPLAY_DESC")
	private String htmlDisplayDesc;

	private String mandatory;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

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

	public String getAttributePath() {
		return this.attributePath;
	}

	public void setAttributePath(String attributePath) {
		this.attributePath = attributePath;
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

	public String getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
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

}