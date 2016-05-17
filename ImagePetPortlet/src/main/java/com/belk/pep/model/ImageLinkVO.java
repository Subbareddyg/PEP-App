/**
 * 
 */
package com.belk.pep.model;

/**
 * Class to hold the Image Link attribute values
 * fetched from database.
 * 
 * Class added For PIM Phase 2 - Regular Item Image Link Attribute
 * Date: 05/13/2016
 * Added By: Cognizant
 */
public class ImageLinkVO {
	
	private String orin;
	private String shotType;
	private String imageURL;
	private String swatchURL;
	private String viewURL;
	/**
	 * @return the orin
	 */
	public String getOrin() {
		return orin;
	}
	/**
	 * @param orin the orin to set
	 */
	public void setOrin(String orin) {
		this.orin = orin;
	}
	/**
	 * @return the shotType
	 */
	public String getShotType() {
		return shotType;
	}
	/**
	 * @param shotType the shotType to set
	 */
	public void setShotType(String shotType) {
		this.shotType = shotType;
	}
	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	/**
	 * @return the swatchURL
	 */
	public String getSwatchURL() {
		return swatchURL;
	}
	/**
	 * @param swatchURL the swatchURL to set
	 */
	public void setSwatchURL(String swatchURL) {
		this.swatchURL = swatchURL;
	}
	/**
	 * @return the viewURL
	 */
	public String getViewURL() {
		return viewURL;
	}
	/**
	 * @param viewURL the viewURL to set
	 */
	public void setViewURL(String viewURL) {
		this.viewURL = viewURL;
	}
	
}
