package com.belk.pep.form;

import java.util.ArrayList;
import java.util.List;

import com.belk.pep.form.StyleInfoDetails;
import com.belk.pep.form.ImageProductDetails;
import com.belk.pep.form.VendorInfoDetails;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.ImageRejectReason;

/**
 * The Class LoginBean.
 */
public class ImageForm {
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/**
	 * 
	 */
	private ArrayList<StyleInfoDetails> styleInfo;
	
	/**
	 * 
	 */
	private ArrayList<ImageProductDetails> imageProductInfo;
	
	/**
	 * 
	 */
	private ArrayList<VendorInfoDetails> vendorInfoList;
	
	/**
	 * 
	 */
	private ArrayList<ContactInfoDetails> contactInfoList;
	
	/**
	 * 
	 */
	private ArrayList<PepDetailsHistory> pepHistoryList;
	
	/**
	 * 
	 */
	private ArrayList<SamleImageDetails> sampleImageDetailList;
	
	/**
	 * 
	 */
	private List<ImageRejectReason> allImageRejectReasons;
	
	/**
	 * @return the sampleImageDetailList
	 */
	public ArrayList<SamleImageDetails> getSampleImageDetailList() {
		return sampleImageDetailList;
	}

	/**
	 * @param sampleImageDetailList the sampleImageDetailList to set
	 */
	public void setSampleImageDetailList(
			ArrayList<SamleImageDetails> sampleImageDetailList) {
		this.sampleImageDetailList = sampleImageDetailList;
	}

	/**
	 * @return the artDirectorDetailsList
	 */
	public ArrayList<ArtDirectorRequestDetails> getArtDirectorDetailsList() {
		return artDirectorDetailsList;
	}

	/**
	 * @param artDirectorDetailsList the artDirectorDetailsList to set
	 */
	public void setArtDirectorDetailsList(
			ArrayList<ArtDirectorRequestDetails> artDirectorDetailsList) {
		this.artDirectorDetailsList = artDirectorDetailsList;
	}

	/**
	 * 
	 */
	private ArrayList<ArtDirectorRequestDetails> artDirectorDetailsList;
	
	
	
	
	public ArrayList<PepDetailsHistory> getPepHistoryList() {
        return pepHistoryList;
    }

    public void setPepHistoryList(ArrayList<PepDetailsHistory> pepHistoryList) {
        this.pepHistoryList = pepHistoryList;
    }

    /**
	 * 
	 * @return
	 */
	public ArrayList<ContactInfoDetails> getContactInfoList() {
        return contactInfoList;
    }
	
	/**
	 * 
	 * @param contactInfo
	 */
    public void setContactInfoList(ArrayList<ContactInfoDetails> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }
    /**
	 * 
	 * @return
	 */
	public ArrayList<VendorInfoDetails> getVendorInfoList() {
        return vendorInfoList;
    }
	/*
	 * 
	 * 
	 */
    public void setVendorInfoList(ArrayList<VendorInfoDetails> vendorInfoList) {
        this.vendorInfoList = vendorInfoList;
    }

    /**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<StyleInfoDetails> getStyleInfo() {
        return styleInfo;
    }

    public void setStyleInfo(ArrayList<StyleInfoDetails> styleInfo) {
        this.styleInfo = styleInfo;
    }

    public ArrayList<ImageProductDetails> getImageProductInfo() {
        return imageProductInfo;
    }

    public void setImageProductInfo(
        ArrayList<ImageProductDetails> imageProductInfo) {
        this.imageProductInfo = imageProductInfo;
    }
    /**
     * Attribute added to show the Image link attribute details in screen.
     * 
     * Attribute added For PIM Phase 2 - Regular Item Image Link Attribute
     * Date: 05/13/2016
     * Added By: Cognizant
     */
    private List<ImageLinkVO> imageLinkVOList;

	/**
	 * @return the imageLinkVOList
	 */
	public List<ImageLinkVO> getImageLinkVOList() {
		return imageLinkVOList;
	}

	/**
	 * @param imageLinkVOList the imageLinkVOList to set
	 */
	public void setImageLinkVOList(List<ImageLinkVO> imageLinkVOList) {
		this.imageLinkVOList = imageLinkVOList;
	}

	public List<ImageRejectReason> getAllImageRejectReasons() {
		return allImageRejectReasons;
	}

	public void setAllImageRejectReasons(
			List<ImageRejectReason> allImageRejectReasons) {
		this.allImageRejectReasons = allImageRejectReasons;
	}


}
