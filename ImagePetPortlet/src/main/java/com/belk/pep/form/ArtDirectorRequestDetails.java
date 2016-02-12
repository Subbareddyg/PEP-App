/**
 * 
 */
package com.belk.pep.form;

/**
 * @author AFUSZR6
 *
 */
public class ArtDirectorRequestDetails {
	
	private String orinNo;
	private String imageId;
	private String imageType;
	private String imageNote;
	private String imageLocation;
	
	//Column not returned from DB 
	private String ardIndicator;
	
	private String artDirectorNote;
	private String imageStatus;
	private String requestedimageNote;
	private String imageDescription;
	private String additionalInfo;
	private String imageUrlDesc;
	
	
	public String getImageUrlDesc() {
		return imageUrlDesc;
	}
	public void setImageUrlDesc(String imageUrlDesc) {
		this.imageUrlDesc = imageUrlDesc;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	public String getRequestedimageNote() {
		return requestedimageNote;
	}
	public void setRequestedimageNote(String requestedimageNote) {
		this.requestedimageNote = requestedimageNote;
	}
	/**
	 * @return the orinNo
	 */
	public String getOrinNo() {
		return orinNo;
	}
	/**
	 * @param orinNo the orinNo to set
	 */
	public void setOrinNo(String orinNo) {
		this.orinNo = orinNo;
	}
	/**
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}
	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}
	/**
	 * @param imageType the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	/**
	 * @return the imageNote
	 */
	public String getImageNote() {
		return imageNote;
	}
	/**
	 * @param imageNote the imageNote to set
	 */
	public void setImageNote(String imageNote) {
		this.imageNote = imageNote;
	}
	/**
	 * @return the imageLocation
	 */
	public String getImageLocation() {
		return imageLocation;
	}
	/**
	 * @param imageLocation the imageLocation to set
	 */
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	/**
	 * @return the ardIndicator
	 */
	public String getArdIndicator() {
		return ardIndicator;
	}
	/**
	 * @param ardIndicator the ardIndicator to set
	 */
	public void setArdIndicator(String ardIndicator) {
		this.ardIndicator = ardIndicator;
	}
	/**
	 * @return the artDirectorNote
	 */
	public String getArtDirectorNote() {
		return artDirectorNote;
	}
	/**
	 * @param artDirectorNote the artDirectorNote to set
	 */
	public void setArtDirectorNote(String artDirectorNote) {
		this.artDirectorNote = artDirectorNote;
	}
	/**
	 * @return the imageStatus
	 */
	public String getImageStatus() {
		return imageStatus;
	}
	/**
	 * @param imageStatus the imageStatus to set
	 */
	public void setImageStatus(String imageStatus) {
		this.imageStatus = imageStatus;
	}
	
	

}
