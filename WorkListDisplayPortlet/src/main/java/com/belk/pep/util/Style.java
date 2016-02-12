package com.belk.pep.util;

public class Style {
	private String id;
	private String source;
	private String dept;
	private String vendor;
	private String vendorStyle;
	private String requestType;
	private String status;
	private String assignedTo;
	private String statusDue;
	public String getStatusDue() {
		return statusDue;
	}
	public void setStatusDue(String statusDue) {
		this.statusDue = statusDue;
	}



	private String completionDate;
	
	public Style(String id,String source, String dept, String vendor, String vendorStyle,String requestType,String status,String assignedTo,String completionDate) {
		super();
		this.id = id;
		this.source = source;
		this.dept = dept;
		this.vendor = vendor;
		this.vendorStyle = vendorStyle;
		this.requestType = requestType;
		this.status = status;
		this.assignedTo = assignedTo;
		this.completionDate = completionDate;
	
	}
	public Style(){}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVendorStyle() {
		return vendorStyle;
	}
	public void setVendorStyle(String vendorStyle) {
		this.vendorStyle = vendorStyle;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssigned() {
		return assignedTo;
	}
	public void setAssigned(String assigned) {
		this.assignedTo = assigned;
	}
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
		
	
	
	public String toString() {
		StringBuffer contactStr = new StringBuffer("WorkFlow Details =[ Id = ");
		contactStr.append(id);
		contactStr.append(",  source = ");
		contactStr.append(source);
		contactStr.append(", dept = ");
		contactStr.append(dept);
		contactStr.append(",  vendor = ");
		contactStr.append(vendor);
		contactStr.append(",  vendorStyle = ");
		contactStr.append(vendorStyle);
		contactStr.append(" ]");
		return contactStr.toString();
	}
	
}
