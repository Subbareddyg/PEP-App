/**
 * 
 */
package com.belk.pep.form;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 *
 */
public class FileUploadForm {
	
	private CommonsMultipartFile fileData;
	private String message;
	
	/**
	 * @return the fileData
	 */
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
