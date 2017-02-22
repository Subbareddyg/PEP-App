package com.belk.pep.model;

/**
 * Class holds attributes of Image Reject Reasons 
 * @author afuaxj4
 */
public class ImageRejectReason {
	private String reasonCode;
	private String rejectReason;

	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
}
